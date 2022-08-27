import os
import tkinter as tk
from tkinter.filedialog import *  # 如果已经导入了所有tkinter也要再次导入

import markdown2
import shutil
from bs4 import BeautifulSoup
from bson import Binary, ObjectId
from datetime import datetime
from pymongo import MongoClient
from urllib.parse import unquote

from convert_docx_to_html import convert_docx_to_html
from reformat_html import reformat_docx_html


class Article:
    file_type = ''
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
        return None, None
    print("find same title, delete the old one")
    content = db_article["content"]
    for src in find_image_tags(content):
        image_id = src.split("/")[-1]
        print("deleting image: " + image_id)
        mongo_client.file.img.delete_one({"_id": ObjectId(image_id)})
    # 被删除的文档中，源文档的id和源文档的创建时间是需要保留的，以供新的文件复用
    return db_article["_id"], db_article["createdTime"]


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
        # 滚动条
        self.scroll = tk.Scrollbar(self, orient="vertical", command=self.scroll_callback)
        self.scroll.grid(row=2, column=4, sticky="NS")
        # 显示导入的内容：
        tk.Label(self, text="title").grid(row=1, column=0)
        self.article_title_List_box = tk.Listbox(self, width=40, yscrollcommand=self.scroll.set)
        self.article_title_List_box.grid(row=2, column=0)
        self.article_title_List_box.bind("<MouseWheel>", self.mouse_wheel_callback)
        tk.Label(self, text="category").grid(row=1, column=1)
        self.article_category_List_box = tk.Listbox(self, width=15, yscrollcommand=self.scroll.set)
        self.article_category_List_box.grid(row=2, column=1)
        self.article_category_List_box.bind("<MouseWheel>", self.mouse_wheel_callback)
        tk.Label(self, text="tags").grid(row=1, column=2)
        self.article_tags_List_box = tk.Listbox(self, yscrollcommand=self.scroll.set)
        self.article_tags_List_box.grid(row=2, column=2)
        self.article_tags_List_box.bind("<MouseWheel>", self.mouse_wheel_callback)
        # 显示执行结果
        tk.Label(self, text="result").grid(row=1, column=3)
        self.result_list_box = tk.Listbox(self, width=25)
        self.result_list_box.grid(row=2, column=3)
        self.result_list_box.bind("<MouseWheel>", self.mouse_wheel_callback)

        self.convert_to_html_button = tk.Button(self, text="convert to html", command=self.convert_to_html_callback)
        self.convert_to_html_button.grid(row=3, column=0)

        self.upload_button = tk.Button(self, text="upload", command=self.upload_callback)
        self.upload_button.grid(row=3, column=1)

        self.convert_and_upload_button = tk.Button(self, text="convert_and_upload",
                                                   command=self.convert_and_upload_callback)
        self.convert_and_upload_button.grid(row=3, column=2)

        self.all_in_one_button = tk.Button(self, text="All in one", command=self.all_in_one_callback)
        self.all_in_one_button.grid(row=3, column=3)

        self.clear_selection_button = tk.Button(self, text="clear selection", command=self.clear_selection_callback)
        self.clear_selection_button.grid(row=2, column=5)

        self.clear_all_button = tk.Button(self, text="clear all", command=self.clear_callback)
        self.clear_all_button.grid(row=3, column=4)

    def mouse_wheel_callback(self, event):
        # 还是不怎么管用
        self.article_title_List_box.yview("scroll", event.delta, "units")
        self.article_category_List_box.yview("scroll", event.delta, "units")
        self.article_tags_List_box.yview("scroll", event.delta, "units")
        self.result_list_box.yview("scroll", event.delta, "units")
        # this prevents default bindings from firing, which
        # would end up scrolling the widget twice
        return "break"

    def scroll_callback(self, *args):
        self.article_title_List_box.yview(*args)
        self.article_category_List_box.yview(*args)
        self.article_tags_List_box.yview(*args)
        self.result_list_box.yview(*args)

    def add_file(self, file_path_list):
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
                article.file_type = ".docx"
                self.article_list.append(article)
                self.__add_information("added docx file")
            elif extension == ".md":
                article.file_type = ".md"
                self.article_list.append(article)
                self.__add_information("added markdown file")

    def select_dict_button_callback(self):
        path_str = askdirectory()
        L = []
        for root, dirs, files in os.walk(path_str):
            for file in files:
                # 预防临时文件
                if file.startswith("~"):
                    continue
                extern = os.path.splitext(file)[1]
                if extern == '.docx' or extern == ".md":
                    full_path = os.path.join(root, file).replace("\\", "/")
                    L.append(full_path)
        self.add_file(L)
        pass

    def select_file_button_callback(self):
        file_path_list = askopenfilenames()
        self.add_file(file_path_list)

    def convert_to_html_callback(self):
        self.__clear_information()
        for article in self.article_list:
            if article.file_type == ".docx":
                docx_path = article.original_path
                file_name = os.path.basename(docx_path).split(".")[0]
                print("converting ", file_name, ".docx to html")
                # save path should be absolute path
                save_finder = os.getcwd() + "/cache/"
                convert_docx_to_html(docx_path, save_finder)
                article.html_path = save_finder + file_name + ".html"
                # reformat_docx_html(save_path)
                self.__add_information("convert docx to html, done!")
            elif article.file_type == ".md":
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
            "mongodb://wangzilin:19961112w@47.97.154.231:27017/?authSource=admin&readPreference=primary"
            "&appname=MongoDB%20Compass&ssl=false")
        for article in self.article_list:
            try:
                # convert html to article
                print("processing：", article.title)
                deleted_id, created_time = delete_article_if_exist(article.title, article.category, article.tags,
                                                                   client)
                html_path = article.html_path
                article_html = open(html_path, "r", encoding='utf-8').read()
                img_tags = find_image_tags(article_html)
                print("extract %d image(s)" % (len(img_tags)))
                # replace <v:imagedata.../> to <img/>
                cover_id = ''  # article's cover, if there is no img, it will be '', else it take the last img as cover
                for img_tag in img_tags:
                    img_local_url = img_tag
                    img_local_path = "cache/" + unquote(img_local_url, 'utf-8')  # 去掉转义字符
                    # upload to mongodb
                    img_id = upload_image(img_local_path, client)
                    cover_id = str(img_id)
                    img_url = "https://zilinn.wang:8443/api/img/" + cover_id
                    article_html = article_html.replace(img_local_url, img_url)
                # get original text (just for md):
                content_md = ''
                if article.file_type == ".md":
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
                    "createdTime": datetime.utcnow() if created_time is None else created_time,
                    "editTime": datetime.utcnow(),
                }
                # 如果删除了同名的文件，则本次储存还用原来的id
                if deleted_id is not None:
                    db_article["_id"] = deleted_id
                print("uploading", article.title)
                article_collection = client.blog.article
                article_collection.insert_one(db_article)
                self.__add_information("uploading done!")
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

    def convert_and_upload_callback(self):
        self.convert_to_html_callback()
        self.upload_callback()
        self.clear_callback()

    def all_in_one_callback(self):
        self.select_file_button_callback()
        self.convert_and_upload_callback()

    def clear_selection_callback(self):
        # return index:
        current_selection = self.article_title_List_box.curselection()[0]
        del (self.article_list[current_selection])
        self.article_title_List_box.delete(current_selection)
        self.article_tags_List_box.delete(current_selection)
        self.article_category_List_box.delete(current_selection)
        self.result_list_box.delete(current_selection)

    def clear_callback(self):
        self.article_list = []
        self.__clear_information()
        self.article_title_List_box.delete(0, 'end')
        self.article_category_List_box.delete(0, 'end')
        self.article_tags_List_box.delete(0, 'end')
        shutil.rmtree("cache")
        print("All clear!")

    def __clear_information(self):
        self.result_list_box.delete(0, 'end')

    def __add_information(self, information: str):
        self.result_list_box.insert('end', information)


window = Framework()
window.title("主窗口名称")
window.mainloop()

# client = MongoClient(
#     "mongodb://wangzilin:19961112w@47.97.154.231:27017/?authSource=admin&readPreference=primary"
#     "&appname=MongoDB%20Compass&ssl=false")
