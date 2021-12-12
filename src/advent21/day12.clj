(ns advent21.day12
  (:require [clojure.string :as str]
            [advent21.utils.input :as i]))

(def start "start")
(def end "end")

(defn parse-input [filename]
  (apply merge-with into
         (mapcat (fn [row]
                   (let [[to from] (str/split row #"-")]
                     [(hash-map to [from]) {from [to]}]))
                 (i/load-input filename))))

(defn big? [cave] (= cave (str/upper-case cave)))

(defn small-caves-once [path]
  (partial (fn [cave] (or (big? cave) (not (contains? (set path) cave))))))

(defn multiple-small-caves [path]
  (some #(> (count %) 1) (vals (group-by identity (filter #(not (big? %)) path)))))

(defn one-small-cave-twice [path]
  (partial (fn [cave] (and (not (= cave start))
                           (or (big? cave)
                               (not (contains? (set path) cave))
                               (not (multiple-small-caves path)))))))

(defn explore [graph path next-caves-filter]
  (if (= (last path) end)
    (str/join "-" path)
    (let [possible-caves (filter (next-caves-filter path)
                                 (graph (last path)))
          new-paths (map #(explore graph (conj path %) next-caves-filter) possible-caves)]
      (if (string? new-paths) new-paths (flatten new-paths)))))

(defn explore-cave-system [graph next-caves-filter] (explore graph [start] next-caves-filter))

(defn part1 [filename] (count (explore-cave-system (parse-input filename) small-caves-once)))

(defn part2 [filename] (count (explore-cave-system (parse-input filename) one-small-cave-twice)))
