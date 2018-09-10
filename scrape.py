import requests
from random import choice
from bs4 import BeautifulSoup
from lxml import html
def get_random_ua():
    with open('ua.txt', 'r') as f:
        ua_list = [line.strip() for line in f]
        return choice(ua_list)
    
headers = {
        'user-agent': get_random_ua(),
        'referer': 'https://google.com'
    }



def get_lyrics():
    with open("titles.txt", "r") as f:
        lines = [line.strip() for line in f]
        for i in range(0, len(lines), 2):
            query = (lines[i] + " " + lines[i + 1] + " lyrics").replace(" ", "+")
            r = requests.get("https://google.com/search?q=" + query, headers=headers)
            soup = BeautifulSoup(r.content, "lxml")
            # print(r.text)
            link = soup.select_one("a[href*=azlyrics]")
            if link:
                print(query)
                lyrics_req = r = requests.get(link['href'], headers=headers)
                tree = html.fromstring(lyrics_req.text)
                # print(r.text)
                with open("lyrics/" + query.replace("+", "_") + ".txt", "w") as f:
                    f.write("\n".join([ele.strip() for ele in tree.xpath("/html/body/div[3]/div/div[2]/div[5]//text()")]))

get_lyrics()