from wordcloud import WordCloud, STOPWORDS

with open("combined.txt", "r") as f:
    text = " ".join(f.readlines())
stopwords = set(STOPWORDS)

wc = WordCloud(stopwords=stopwords, background_color="white", width=1000, height=700)
wc.generate(text)
wc.to_file("wc.png")