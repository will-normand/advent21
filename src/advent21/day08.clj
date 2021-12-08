(ns advent21.day08
  (:require [clojure.string :as str]
            [advent21.utils.input :as i]
            [clojure.set :as set]))

(def input (i/load-input "day08test"))

(def parse-row (fn [row]
                 (->> (str/split row #"\|")
                      (map #(-> %
                                (str/trim)
                                (str/split #" ")
                                )))))

(defn parse-input [rows] (map parse-row rows))

(defn replace-easy-digits [output]
  (map (fn [code] (cond
                    (= (count code) 2) 1
                    (= (count code) 3) 7
                    (= (count code) 4) 4
                    (= (count code) 7) 8
                    :else code))
       output))

(defn digit-map [digits] (into {} (map #(vector % nil) digits)))

(defn find-easy-digits [output]
  (map (fn [[code _]]
         (let [code-set (set code)]
           (cond
             (= (count code) 2) [code-set 1]
             (= (count code) 3) [code-set 7]
             (= (count code) 4) [code-set 4]
             (= (count code) 7) [code-set 8]
             :else [code-set nil])))
       output))

(defn only [x] {:pre [(nil? (next x))]} (first x))

(defn get-code [codes digit] (first (first (filter (fn [[_ d]] (= d digit)) codes))))

(defn freqs [codes]
  (let [code-sets (map first codes)]
    (into {} (map (fn [[k v]] [k (count v)]))
          (group-by first
                    (for [letter [\a \b \c \d \e \f \g]
                          contain (filter #(contains? % letter) code-sets)] [letter contain])))))

(defn top [codes] (let [code-7 (get-code codes 7)
                        code-1 (get-code codes 1)]
                    (first (set/difference code-7 code-1))))

(defn bottom-right
  "The bottom right value appears in 9 numbers"
  [codes]
  (->> (freqs codes)
       (filter (fn [[_ v]] (= v 9)))
       (only)
       (key)))

(defn bottom-left
  "The bottom left value appears in 4 numbers"
  [codes]
  (->> (freqs codes)
       (filter (fn [[_ v]] (= v 4)))
       (only)
       (key)))

(defn top-left
  "The bottom left value appears in 6 numbers"
  [codes]
  (->> (freqs codes)
       (filter (fn [[_ v]] (= v 6)))
       (only)
       (key)))

(defn centre
  "The centre value is in 4, not in 7, and isn't the top-left"
  [codes]
  (only (set/difference (set/difference (get-code codes 4) (get-code codes 7)) #{(top-left codes)})))

(defn bottom
  "The bottom value appears in 7 numbers, and is not the centre"
  [codes]
  (let [centre (centre codes)]
    (->> (freqs codes)
         (filter (fn [[k v]] (and (= v 7) (not= k centre))))
         (only)
         (key))))

(defn top-right
  "The top-right value appears in 8 numbers, and is not the top"
  [codes]
  (let [top (top codes)]
    (->> (freqs codes)
         (filter (fn [[k v]] (and (= v 8) (not= k top))))
         (only)
         (key))))

(def zero #{top top-left bottom-left bottom bottom-right top-right})
(def two #{top top-right centre bottom-left bottom})
(def three #{top top-right centre bottom-right bottom})
(def five #{top top-left centre bottom-right bottom})
(def six #{top top-left centre bottom-left bottom-right bottom})
(def nine #{top top-left top-right centre bottom-right bottom})

(defn numbers [codes]
  (let [make-key (fn [number] (set (map #(% codes) number)))]
    (->> codes
         (filter (fn [[_ v]] (not (nil? v))))
         (cons [(make-key zero) 0])
         (cons [(make-key two) 2])
         (cons [(make-key three) 3])
         (cons [(make-key five) 5])
         (cons [(make-key six) 6])
         (cons [(make-key nine) 9])
         (into {}))))

(defn seq-to-int [digits] (i/parse-int (str/join (map str digits))))

(defn lookup-output [[input output]]
  (let [codes (find-easy-digits (digit-map input))
        numbers-map (numbers codes)
        output-set (map set output)]
    (seq-to-int (map #(numbers-map %) output-set))))

(defn part1 [filename]
  (let [output (map second (parse-input (i/load-input filename)))
        easy-replaced (map replace-easy-digits output)]
    (count (filter int? (flatten easy-replaced)))))

(defn part2 [filename]
  (let [input (parse-input (i/load-input filename))]
    (reduce + (map lookup-output input))))
