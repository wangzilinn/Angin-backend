from pydocx import PyDocX

html = PyDocX.to_html("test.docx")
f = open("test.html", 'w', encoding="utf-8")
f.write(html)
f.close()
