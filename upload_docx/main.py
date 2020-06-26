import win32com.client

word = win32com.client.Dispatch('Word.Application')

doc = word.Documents.Add(r"C:\Case\case-Java\191212_angin_backend\upload_docx\test.docx")
doc.SaveAs(r'C:\Case\case-Java\191212_angin_backend\upload_docx\test.html', FileFormat=8)
doc.Close()

word.Quit()
