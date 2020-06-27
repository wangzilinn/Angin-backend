import win32com.client

word = win32com.client.Dispatch('Word.Application')

doc = word.Documents.Add(r"C:\Case\case-Java\191212_angin_backend\upload_docx\test.docx")
doc.SaveAs(r'C:\Case\case-Java\191212_angin_backend\upload_docx\test.html', FileFormat=8)
doc.Close()

word.Quit()

infile = open("test.html", "r", encoding='gb2312')  # 打开文件
outfile = open("test2.html", "w", encoding='utf-8')  # 内容输出
for line in infile:  # 按行读文件，可避免文件过大，内存消耗
    outfile.write(line.replace('gb2312', 'utf-8'))  # first is old ,second is new
infile.close()  # 文件关闭
outfile.close()
