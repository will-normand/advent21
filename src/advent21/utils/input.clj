(ns advent21.utils.input
  (:require [clojure.string :as str]))

(defn load-input
  "Reads the specified file, splits into lines and returns a sequence of strings"
  [filename]
  (str/split-lines (slurp (str "resources/" filename))))

(defn parse-int [s] (Integer/parseInt s))

(defn parse-int-row
  "Parses an input file as a single-line list of Integers."
  [filename]
  (->> (-> (load-input filename)
           (first)
           (str/split #","))
       (map parse-int)))
