import os

from bs4 import BeautifulSoup


def reformat_docx_html(html_path: str):
    with open(html_path, 'r', encoding="gb18030", errors='ignore') as file:
        original_file = file.read()
    os.remove(html_path)
    sp = BeautifulSoup(original_file, 'lxml')
    clear_tag_list = ["h1", "h2", "h3", "h4", "h5"]
    for clear_tag in clear_tag_list:
        for tag in sp.find_all(clear_tag):
            new_tag = sp.new_tag(clear_tag)
            new_tag.string = tag.get_text()
            tag.replace_with(new_tag)
    # 修改图片标签v:imagedata为img
    html = str(sp).replace("v:imagedata", "img")
    with open(html_path, 'w', encoding="utf-8") as fp:
        # write the current soup content
        fp.write(html)
