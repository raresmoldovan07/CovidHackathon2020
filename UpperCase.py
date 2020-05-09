import sys

def main(text):
    return text.upper()

if __name__ == '__main__':
    file = open("file.txt", 'w')
    file.write(main(sys.argv[1]))