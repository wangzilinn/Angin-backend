import requests

res = requests.get('https://random-ize.com/random-art-gallery/gal-f.php')
print(res.text)
