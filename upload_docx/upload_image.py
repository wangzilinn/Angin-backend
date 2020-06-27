import os
from datetime import datetime

from bson import Binary
from pymongo import MongoClient

# pprint library is used to make the output look more pretty

# connect to MongoDB, change the << MONGODB URL >> to reflect your own connection string
client = MongoClient("mongodb://wangzilin:19961112w@47.103.194.29:27017/?authSource=admin&readPreference=primary"
                     "&appname=MongoDB%20Compass&ssl=false")
db = client.file
image_collection = db.img
imageFile = open("image.png", "rb").read()
imageBytes = bytearray(imageFile)
size = os.path.getsize("image.png")
image = {
    "name": "pythonTest",
    "createdTime": datetime.utcnow(),
    "contentType": "image/png",
    "size": size,
    "content": Binary(imageBytes)
}
result = image_collection.insert_one(image)
print(result)
