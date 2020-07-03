import tkinter as tk
from tkinter.filedialog import *  # 如果已经导入了所有tkinter也要再次导入

import markdown2
import shutil
import win32com.client
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


test_str = r"C:\Users\78286\OneDrive\Knowledge\lang_Python\Packages\123\函数整理_matplotlib.docx"


def get_article_meta(article_path: str) -> (str, str, str):
    title = os.path.basename(article_path).split(".")[0]
    information_part = article_path.split("Knowledge\\")[1]
    # e.g.information_part = 'lang_Python\\Packages\\函数整理_matplotlib.docx'
    category = information_part.split("\\")[0]
    tag_list = []
    for tag in information_part.split("\\")[1:-1]:
        tag_list.append(tag)
    return category, tag_list, title


print(get_article_meta(test_str))


class Framework(tk.Tk):
    article_list = []

    def __init__(self, *args, **kwargs):
        tk.Tk.__init__(self, *args, **kwargs)
        self.__place_widgets()

    def __place_widgets(self):
        self.select_button = tk.Button(self, text="select files", command=self.select_button_callback)
        self.select_button.grid(row=0, column=0)
        # 显示导入的内容：
        tk.Label(self, text="title").grid(row=1, column=0)
        self.article_title_List_box = tk.Listbox(self)
        self.article_title_List_box.grid(row=2, column=0)
        tk.Label(self, text="category").grid(row=1, column=1)
        self.article_category_List_box = tk.Listbox(self)
        self.article_category_List_box.grid(row=2, column=1)
        tk.Label(self, text="tags").grid(row=1, column=2)
        self.article_tags_List_box = tk.Listbox(self)
        self.article_tags_List_box.grid(row=2, column=2)
        # 显示执行结果
        tk.Label(self, text="result").grid(row=1, column=3)
        self.result_list_box = tk.Listbox(self)
        self.result_list_box.grid(row=2, column=3)

        self.convert_to_html_button = tk.Button(self, text="convert to html", command=self.convert_to_html_callback)
        self.convert_to_html_button.grid(row=3, column=0)

        self.upload_button = tk.Button(self, text="upload", command=self.upload_callback)
        self.upload_button.grid(row=3, column=1)

        self.clear_button = tk.Button(self, text="clear", command=self.clear_callback)
        self.clear_button.grid(row=3, column=2)

        self.all_in_one_button = tk.Button(self, text="All in one", command=self.all_in_one_callback)
        self.all_in_one_button.grid(row=3, columnspan=3)

    def select_button_callback(self):
        folder = os.path.exists("cache")
        if not folder:
            os.makedirs("cache")
        file_path_list = askopenfilenames()
        for file_path in file_path_list:
            if not os.path.isfile(file_path):  # exclude shortcuts
                continue
            article = Article()
            article.category, article.tags, article.title = get_article_meta(file_path)
            article.original_path = file_path
            extension = os.path.splitext(file_path)[1]
            if extension == ".docx":
                article.doc_type = ".docx"
                self.article_list.append(article)
                self.result_list_box.insert("added docx file")
            elif extension == ".md":
                article.doc_type = ".md"
                self.article_list.append(article)
                self.result_list_box.insert("added markdown file")

    def convert_to_html_callback(self):
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

    def upload_callback(self):
        client = MongoClient(
            "mongodb://wangzilin:19961112w@47.103.194.29:27017/?authSource=admin&readPreference=primary"
            "&appname=MongoDB%20Compass&ssl=false")
        for article in self.article_list:
            # convert html to article
            print("processing：", article.title)
            html_path = article.html_path
            article_html = open(html_path, "r", encoding='utf-8').read()  # notice:gb2312
            img_tags = re.findall(r"src=\".*\" ", article_html)
            print("extract %d image(s)" % (len(img_tags)))
            # replace <v:imagedata.../> to <img/>
            article_html = article_html.replace("v:imagedata", "img")
            cover_id = ''  # article's cover
            for img_tag in img_tags:
                img_local_url = img_tag.split("\"")[1]
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
            article = {
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
            print("uploading", article["title"])
            article_collection = client.blog.article
            article_collection.insert_one(article)
            print("done!")

    def clear_callback(self):
        self.article_list = []
        shutil.rmtree("cache")
        print("All clear!")

    def all_in_one_callback(self):
        self.select_button_callback()
        self.convert_to_html_callback()
        self.upload_callback()
        self.clear_callback()

# window = Framework()
# window.title("主窗口名称")
# window.mainloop()
