import fnmatch
import os
import sys
import tempfile
import webbrowser

from pygments import highlight
from pygments.formatters import HtmlFormatter
from pygments.lexers import get_lexer_for_filename
from itertools import chain

# The directory you want to traverse
base_dir = sys.argv[1]

# Regex pattern for the files to include
pattern = input('pattern (default *, all files, example: *.py,*.java = PYTHON and JAVA files): ').strip()
if len(pattern) == 0:
    pattern = ['*']
else:
    pattern = pattern.split(',')
type_format = int(input('formatting type:\n0) no highlight\n1) highlight\n2) highlight+linenumbers\n3) no highlight+linenumbers\nYOUR CHOICE(0/1/2/3): ').strip())
if type_format == 2:
    # Formatter with line numbers
    formatter = HtmlFormatter(style="colorful", full=True, linenos='table')
elif type_format == 1:
    formatter = HtmlFormatter(style="colorful", full=True)
elif type_format == 0:
    formatter = HtmlFormatter()
elif type_format == 3:
    formatter = HtmlFormatter(linenos='table')

else:
    print('not 0, 1 or 2 or 3')
    exit(0)

css_styles = formatter.get_style_defs('.codehilite')
if type_format != 0:
    html_output = f"<html><head><style>{css_styles}</style></head><body>"
else:
    html_output = f"<html><head></head><body>"


# Function to traverse directories and add highlighted files to HTML
def process_directory(directory):
    global html_output
    for root, dirs, files in os.walk(directory):
        for filename in chain.from_iterable([fnmatch.filter(files, p) for p in pattern]):
            file_path = os.path.join(root, filename)
            with open(file_path, 'r', encoding='utf-8') as file:
                code = file.read()
                # Get the appropriate lexer for the current file based on its extension
                try:
                    lexer = get_lexer_for_filename(file_path)
                    # Add filename as a heading and the highlighted code
                    hlc = highlight(code, lexer, formatter)
                    html_output += (
                        f"<h1>{os.path.relpath(file_path, start=base_dir).replace(chr(92), '/')}</h1>"
                        f"{hlc}"
                    )
                except:
                    pass


# Start processing the directory
process_directory(base_dir)

html_output += "</body></html>"

# Write to a temporary HTML file and open in the default web browser
with tempfile.NamedTemporaryFile('w', delete=False, suffix='.html') as temp_file:
    temp_file.write(html_output)
    temp_file_path = temp_file.name

# Open the HTML file in the default web browser
webbrowser.open(f'file://{temp_file_path}')

# Note: The user should manually copy the rendered content from the web browser and paste into Word.
