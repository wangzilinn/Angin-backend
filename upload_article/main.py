import tkinter as tk
from datetime import datetime
from tkinter.filedialog import *  # 如果已经导入了所有tkinter也要再次导入
from urllib.parse import unquote

import markdown2
import win32com.client
from bson import Binary
from pymongo import MongoClient


class Article:
    doc_type = ''
    original_path = ''
    html_path = ''
    title = ''
    category = ''


article_list = []


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


def select_button_callback():
    global article_list
    file_path_list = askopenfilenames()  # show an "Open" dialog box and return the path to the selected file
    for file_path in file_path_list:
        extension = os.path.splitext(file_path)[1]
        article = Article()
        article.original_path = file_path
        article.title = os.path.basename(file_path).split(".")[0]
        article.category = file_path.split("/")[-2]
        if extension == ".docx":
            article.doc_type = ".docx"
            article_list.append(article)
            print("add docx file:", file_path)
        elif extension == ".md":
            article.doc_type = ".md"
            article_list.append(article)
            print("add markdown file:", file_path)


def test_callback():
    global article_list
    print(article_list)


def convert_to_html_callback():
    global article_list
    word = win32com.client.Dispatch('Word.Application')
    for article in article_list:
        if article.doc_type == ".docx":
            docx_path = article.original_path
            doc = word.Documents.Add(docx_path)
            file_name = os.path.basename(docx_path).split(".")[0]
            print("converting ", file_name, ".docx to html")
            # save path should be absolute path
            save_path = os.getcwd() + "\\" + file_name + ".html"
            article.html_path = save_path
            doc.SaveAs(save_path, FileFormat=8)
            doc.Close()
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
            save_path = os.getcwd() + "\\" + file_name + ".html"
            article.html_path = save_path
            html_file = open(save_path, "w", encoding="gb2312")  # word 编码也是gb2312，与word相统一
            html_file.write(final_html)


def upload_callback():
    global article_list
    client = MongoClient("mongodb://wangzilin:19961112w@47.103.194.29:27017/?authSource=admin&readPreference=primary"
                         "&appname=MongoDB%20Compass&ssl=false")
    for article in article_list:
        # convert html to article
        print("processing：", article.title)
        html_path = article.html_path
        article_html = open(html_path, "r", encoding='gb2312').read()  # notice:gb2312
        img_tags = re.findall(r"src=\".*\" ", article_html)
        print("extract %d image(s)" % (len(img_tags)))
        # replace <v:imagedata.../> to <img/>
        article_html = article_html.replace("v:imagedata", "img")
        cover_id = ''  # article's cover
        for img_tag in img_tags:
            img_local_url = img_tag.split("\"")[1]
            img_local_path = unquote(img_local_url, 'utf-8')  # 去掉转义字符
            # upload to mongodb
            img_id = upload_image(img_local_path, client)
            cover_id = img_id
            img_url = "https://zilinn.wang:8443/api/img/" + str(img_id)
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
            "state": "release",
            "cover": cover_id,
            "createdTime": datetime.utcnow(),
            "editTime": datetime.utcnow(),
        }
        print("uploading", article["title"])
        article_collection = client.blog.article
        article_collection.insert_one(article)
        print("done!")


window = tk.Tk()  # 创建主窗口
select_button = tk.Button(window, text="select files", command=select_button_callback)
select_button.pack()

convert_to_html_button = tk.Button(window, text="convert to html", command=convert_to_html_callback)
convert_to_html_button.pack()

upload_button = tk.Button(window, text="upload", command=upload_callback)
upload_button.pack()

test_button = tk.Button(window, text="test", command=test_callback)
test_button.pack()

window.title("主窗口名称")
window.mainloop()
