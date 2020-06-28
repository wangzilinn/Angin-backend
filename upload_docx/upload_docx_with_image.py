import os
import re
from datetime import datetime

import win32com.client
from bson import Binary
from pymongo import MongoClient


def upload_image(image_location: str, mongo_client) -> str:
    image_collection = mongo_client.file.img
    image_bytes = bytearray(open(image_location, "rb").read())
    size = os.path.getsize(image_location)
    # extract extension:
    extension = os.path.splitext(image_location)[1]
    image = {
        "name": "docx img",
        "createdTime": datetime.utcnow(),
        "contentType": "image/" + extension,
        "size": size,
        "content": Binary(image_bytes)
    }
    result = image_collection.insert_one(image)
    return result.inserted_id


def generate_gb2312_html(docx_location: str, html_location: str):
    # call word to generate html: gb2312 encoded
    word = win32com.client.Dispatch('Word.Application')
    doc = word.Documents.Add(docx_location)
    doc.SaveAs(html_location, FileFormat=8)
    doc.Close()


def convert_gb2312_html_to_article(html_location: str, mongo_client):
    gb2312file = open(html_location, "r", encoding='gb2312')  # 打开文件
    article_html = gb2312file.read()
    img_tags = re.findall(r"src=\".*\" ", article_html)
    print("extract {0} image(s)", format(len(img_tags)))
    # replace <v:imagedata.../> to <img/>
    article_html = article_html.replace("v:imagedata", "img")
    for img_tag in img_tags:
        img_local_url = img_tag.split("\"")[1]
        print(img_local_url)
        # upload to mongodb
        img_id = upload_image(img_local_url, mongo_client)
        img_url = "https://zilinn.wang:8443/api/img/" + str(img_id)
        article_html = article_html.replace(img_local_url, img_url)

    return {
        "title": os.path.basename(html_location).split(".")[0],  # just file name, no extension
        "author": "wangzilin",
        "content": article_html,
        "state": "release",
        "createdTime": datetime.utcnow(),
        "editTime": datetime.utcnow(),
    }


# generate_gb2312_html(r"C:\Case\case-Java\191212_angin_backend\upload_docx\test.docx",
#                      r"C:\Case\case-Java\191212_angin_backend\upload_docx\test2.html")


client = MongoClient("mongodb://wangzilin:19961112w@47.103.194.29:27017/?authSource=admin&readPreference=primary"
                     "&appname=MongoDB%20Compass&ssl=false")
article = convert_gb2312_html_to_article("test2.html", client)

#
article_collection = client.blog.article
#
# generate_gb2312_html(r"C:\Case\case-Java\191212_angin_backend\upload_docx\test2.docx",
#                      r"C:\Case\case-Java\191212_angin_backend\upload_docx\test2.html")
# article = convert_gb2312_html_to_article("test2.html")
article_collection.insert_one(article)
