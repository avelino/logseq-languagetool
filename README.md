# Logseq LanguageTool Plugin

This is a [Logseq](https://logseq.com/) plugin to check grammar and spelling using [LanguageeTool](https://languagetool.org/).

Written in [ClojureScript](https://clojurescript.org/) with [shadow-cljs](https://github.com/thheller/shadow-cljs) and [babashka (`bb`)](https://github.com/babashka/babashka) as the main tooling.

> ⚠️ **developing**: this plugin is still under development. It is not ready for production use.

![marketplace in developer](https://raw.githubusercontent.com/avelino/logseq-languagetool/main/resources/screenshot-01.png)

## development

run in development mode *(watch)*:

```sh
bb run dev
```

* Enable developer mode in Logseq
* Click "Load unpacked plugin" to open this folder
* Click the "Reload" button to refresh latest changes

## Release

```sh
bb run build
```
