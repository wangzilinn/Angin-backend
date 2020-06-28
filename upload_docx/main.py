import tkinter as tk
from datetime import datetime
from tkinter.filedialog import *  # 如果已经导入了所有tkinter也要再次导入
from urllib.parse import unquote

import win32com.client
from bson import Binary
from pymongo import MongoClient

docx_path_list = []
md_path_list = []
html_path_list = []


def upload_image(image_path: str, mongo_client) -> str:
    image_collection = mongo_client.file.img
    image_bytes = bytearray(open("./" + image_path, "rb").read())
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
    file_path_list = askopenfilenames()  # show an "Open" dialog box and return the path to the selected file
    global docx_path_list
    global md_path_list
    for file_path in file_path_list:
        extension = os.path.splitext(file_path)[1]
        if extension == ".docx":
            docx_path_list.append(file_path)
            print("add docx file:", file_path)
        elif extension == ".md":
            md_path_list.append(file_path)
            print("add markdown file:", file_path)


def test_callback():
    global docx_path_list
    print(docx_path_list)


def convert_to_html_callback():
    global docx_path_list
    word = win32com.client.Dispatch('Word.Application')
    for docx_path in docx_path_list:
        doc = word.Documents.Add(docx_path)
        file_name = os.path.basename(docx_path).split(".")[0]
        print("converting <", file_name, "> to html")
        # save path should be absolute path
        save_path = os.getcwd() + "/" + file_name + ".html"
        html_path_list.append(save_path)
        doc.SaveAs(save_path, FileFormat=8)
        doc.Close()


def upload_callback():
    global html_path_list
    client = MongoClient("mongodb://wangzilin:19961112w@47.103.194.29:27017/?authSource=admin&readPreference=primary"
                         "&appname=MongoDB%20Compass&ssl=false")
    for html_path in html_path_list:
        # convert html to article
        print("processing：", html_path)
        gb2312file = open(html_path, "r", encoding='gb2312')
        article_html = gb2312file.read()
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

        # upload article
        article = {
            "title": os.path.basename(html_path).split(".")[0],  # just file name, no extension
            "author": "wangzilin",
            "content": article_html,
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
