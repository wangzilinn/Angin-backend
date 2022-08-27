import pyexcel_xlsx
import requests
from bs4 import BeautifulSoup
from bson import Binary
from datetime import datetime
from pymongo import MongoClient

client = MongoClient("mongodb://wangzilin:19961112w@47.103.194.29:27017/?authSource=admin&readPreference=primary"
                     "&appname=MongoDB%20Compass&ssl=false")
painting_collection = client.file.painting

xls_data = pyexcel_xlsx.get_data(r"painting_dataset.xlsx")
sheet = xls_data["Sheet1"]
for index, line in enumerate(sheet):
    try:
        # 避免数据不全
        if len(line) < 3:
            continue
        # 避免没有url
        url = line[1]
        if url is None:
            continue
        label = line[3].replace("'", "").strip()
        html = requests.get(url, timeout=10)
        sp = BeautifulSoup(html.content, 'lxml')
        img_tag = sp.find(class_="single_img")
        img_url = img_tag.find("img")["src"]
        print(img_url)
        title_tag = sp.find(class_="artwork-title")
        title = title_tag.text
        artist_tag = sp.find(class_="artist")
        artist = artist_tag.text.replace('	', '').strip()
        print(artist)
        image_binary = requests.get(img_url, timeout=15).content
        image = {
            "title": title,
            "artist": artist,
            "detail_url": url,
            "label": label,
            "content": Binary(image_binary),
            "createdTime": datetime.utcnow(),
            "contentType": "image/" + img_url.split(".")[-1]
        }
        painting_collection.insert_one(image)
    except Exception:
        print(Exception)
        print(index)
        continue
