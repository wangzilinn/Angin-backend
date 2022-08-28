import subprocess


def convert_docx_to_html(file_src, dest):
    subprocess.check_output(
        ["/Applications/LibreOffice.app/Contents/MacOS/soffice", "--headless", "--convert-to", "html", file_src,
         "--outdir", dest])
    print('convert success!')
