import tkinter as tk
from tkinter.filedialog import *  # 如果已经导入了所有tkinter也要再次导入

import markdown2
import shutil
import win32com.client
from bs4 import BeautifulSoup
from bson import Binary
from datetime import datetime
from pymongo import MongoClient
from urllib.parse import unquote

from reformat_html import reformat_docx_html


class Article:
    doc_type = ''
    original_path = ''
    html_path = ''
    title = ''
    category = ''
    tags = []


def find_image_tags(html: str):
    soup = BeautifulSoup(html, 'lxml')
    img_tags = soup.find_all("img")
    return list(map(lambda tag: tag.attrs['src'], img_tags))  # 正则表达式最后有个">"要去掉


def upload_image(image_path: str, mongo_client) -> str:
    image_collection = mongo_client.file.img
    image_bytes = bytearray(open(image_path, "rb").read())
    size = os.path.getsize(image_path)
    # extract extension:
    extension = os.path.splitext(image_path)[1]
    image = {
        "name": "docx img",
        "createdTime": datetime.utcnow(),
        "contentType": "image/" + extension,
        "size": size,
        "content": Binary(image_bytes)
    }
    print("uploading image :", image_path)
    result = image_collection.insert_one(image)
    return result.inserted_id


def get_article_meta(article_path: str) -> (str, str, str):
    title = os.path.basename(article_path).split(".")[0]
    print(article_path)
    information_part = article_path.split("Knowledge/")[1]
    # e.g.information_part = 'lang_Python/Packages/函数整理_matplotlib.docx'
    category = information_part.split("/")[0]
    tag_list = []
    for tag in information_part.split("/")[1:-1]:
        tag_list.append(tag)
    return category, tag_list, title


def delete_article_if_exist(title: str, category: str, tags, mongo_client):
    db_article = mongo_client.blog.article.find_one_and_delete(
        {"title": title, "categoryName": category, "tagNames": tags})
    if db_article is None:
        return False
    print("find same title, delete the old one")
    content = db_article["content"]
    for src in find_image_tags(content):
        image_id = src.split("/")[-1]
        print("deleting image: " + image_id)
        mongo_client.file.img.delete_one({"_id": image_id})


class Framework(tk.Tk):
    article_list = []

    def __init__(self, *args, **kwargs):
        tk.Tk.__init__(self, *args, **kwargs)
        self.__place_widgets()

    def __place_widgets(self):
        self.select_file_button = tk.Button(self, text="select files", command=self.select_file_button_callback)
        self.select_file_button.grid(row=0, column=0)
        self.select_dict_button = tk.Button(self, text="select directory", command=self.select_dict_button_callback)
        self.select_dict_button.grid(row=0, column=1)
        # 显示导入的内容：
        tk.Label(self, text="title").grid(row=1, column=0)
        self.article_title_List_box = tk.Listbox(self, width=40)
        self.article_title_List_box.grid(row=2, column=0)
        tk.Label(self, text="category").grid(row=1, column=1)
        self.article_category_List_box = tk.Listbox(self, width=15)
        self.article_category_List_box.grid(row=2, column=1)
        tk.Label(self, text="tags").grid(row=1, column=2)
        self.article_tags_List_box = tk.Listbox(self)
        self.article_tags_List_box.grid(row=2, column=2)
        # 显示执行结果
        tk.Label(self, text="result").grid(row=1, column=3)
        self.result_list_box = tk.Listbox(self, width=25)
        self.result_list_box.grid(row=2, column=3)

        self.convert_to_html_button = tk.Button(self, text="convert to html", command=self.convert_to_html_callback)
        self.convert_to_html_button.grid(row=3, column=0)

        self.upload_button = tk.Button(self, text="upload", command=self.upload_callback)
        self.upload_button.grid(row=3, column=1)

        self.clear_button = tk.Button(self, text="clear", command=self.clear_callback)
        self.clear_button.grid(row=3, column=2)

        self.all_in_one_button = tk.Button(self, text="All in one", command=self.all_in_one_callback)
        self.all_in_one_button.grid(row=3, columnspan=3)

    def add_file(self, file_path_list):
        self.__clear_information()
        folder = os.path.exists("cache")
        if not folder:
            os.makedirs("cache")
        for file_path in file_path_list:
            if not os.path.isfile(file_path):  # exclude shortcuts
                continue
            article = Article()
            article.category, article.tags, article.title = get_article_meta(file_path)
            article.original_path = file_path
            extension = os.path.splitext(file_path)[1]
            self.article_title_List_box.insert('end', article.title)
            self.article_category_List_box.insert('end', article.category)
            self.article_tags_List_box.insert('end', article.tags)
            if extension == ".docx":
                article.doc_type = ".docx"
                self.article_list.append(article)
                self.__add_information("added docx file")
            elif extension == ".md":
                article.doc_type = ".md"
                self.article_list.append(article)
                self.__add_information("added markdown file")

    def select_dict_button_callback(self):
        path_str = askdirectory()
        L = []
        for root, dirs, files in os.walk(path_str):
            for file in files:
                extern = os.path.splitext(file)[1]
                if extern == '.docx' or extern == ".md":
                    L.append(os.path.join(root, file))
        self.add_file(L)

    def select_file_button_callback(self):
        file_path_list = askopenfilenames()
        # print(file_path_list)
        self.add_file(file_path_list)

    def convert_to_html_callback(self):
        self.__clear_information()
        word = win32com.client.Dispatch('Word.Application')
        for article in self.article_list:
            if article.doc_type == ".docx":
                docx_path = article.original_path
                doc = word.Documents.Add(docx_path)
                file_name = os.path.basename(docx_path).split(".")[0]
                print("converting ", file_name, ".docx to html")
                # save path should be absolute path
                save_path = os.getcwd() + "/cache/" + file_name + ".html"
                article.html_path = save_path
                doc.SaveAs(save_path, FileFormat=8)
                doc.Close()
                reformat_docx_html(save_path)
                self.__add_information("convert docx to html, done!")
            elif article.doc_type == ".md":
                md_path = article.original_path
                file_name = os.path.basename(md_path).split(".")[0]
                print("converting <", file_name, ">.md to html")
                html = markdown2.markdown(open(md_path, "r", encoding="utf-8").read(),
                                          extras=["fenced-code-blocks", "code-color"])
                final_html = """<html lang="en">
                                    <head>
                                        <meta charset="utf-8">
                                        <style type="text/css">
                             """ + open("colorful.css").read() + \
                             """
                                        </style>
                                    </head>
                                    <body>
                            """ + html + \
                             """    </body>
                                </html>
                            """
                save_path = os.getcwd() + "/cache/" + file_name + ".html"
                article.html_path = save_path
                html_file = open(save_path, "w", encoding="utf-8")
                html_file.write(final_html)
                self.__add_information("convert md to html, done!")

    def upload_callback(self):
        self.__clear_information()
        client = MongoClient(
            "mongodb://wangzilin:19961112w@47.103.194.29:27017/?authSource=admin&readPreference=primary"
            "&appname=MongoDB%20Compass&ssl=false")
        for article in self.article_list:
            try:
                # convert html to article
                print("processing：", article.title)
                delete_article_if_exist(article.title, article.category, article.tags, client)
                html_path = article.html_path
                article_html = open(html_path, "r", encoding='utf-8').read()
                img_tags = find_image_tags(article_html)
                print("extract %d image(s)" % (len(img_tags)))
                # replace <v:imagedata.../> to <img/>
                cover_id = ''  # article's cover
                for img_tag in img_tags:
                    img_local_url = img_tag
                    img_local_path = "cache\\" + unquote(img_local_url, 'utf-8')  # 去掉转义字符
                    # upload to mongodb
                    img_id = upload_image(img_local_path, client)
                    cover_id = str(img_id)
                    img_url = "https://zilinn.wang:8443/api/img/" + cover_id
                    article_html = article_html.replace(img_local_url, img_url)
                # get original text (just for md):
                content_md = ''
                if article.doc_type == ".md":
                    content_md = open(article.original_path, "r", encoding="utf-8").read()
                # upload article
                db_article = {
                    "title": article.title,  # just file name, no extension
                    "author": "wangzilin",
                    "content": article_html,
                    "content_md": content_md,
                    "categoryName": article.category,
                    "tagNames": article.tags,
                    "state": "release",
                    "cover": cover_id,
                    "createdTime": datetime.utcnow(),
                    "editTime": datetime.utcnow(),
                }
                print("uploading", article.title)
                self.__add_information("uploading done!")
                article_collection = client.blog.article
                article_collection.insert_one(db_article)
                print("uploading tag if not exist", article.tags)
                tag_collection = client.blog.tag
                for tag in article.tags:
                    tag_collection.update_one({"name": tag, "categoryName": article.category},
                                              {"$set": {"name": tag, "categoryName": article.category}}, upsert=True)
                print("uploading category if not exist", article.category)
                category_collection = client.blog.category
                category_collection.update_one({"name": article.category},
                                               {"$set": {"name": article.category}},
                                               upsert=True)
                print("done!")
            except Exception as ex:
                print(ex)
                continue

    def clear_callback(self):
        self.article_list = []
        self.__clear_information()
        self.article_title_List_box.delete(0, 'end')
        self.article_category_List_box.delete(0, 'end')
        self.article_tags_List_box.delete(0, 'end')
        shutil.rmtree("cache")
        print("All clear!")

    def all_in_one_callback(self):
        self.select_file_button_callback()
        self.convert_to_html_callback()
        self.upload_callback()
        self.clear_callback()

    def __clear_information(self):
        self.result_list_box.delete(0, 'end')

    def __add_information(self, information: str):
        self.result_list_box.insert('end', information)


window = Framework()
window.title("主窗口名称")
window.mainloop()

# client = MongoClient(
#     "mongodb://wangzilin:19961112w@47.103.194.29:27017/?authSource=admin&readPreference=primary"
#     "&appname=MongoDB%20Compass&ssl=false")
