(ns prefix-tree-in-map.core)

(defn add-word [words tab i]
  (let [w (nth words i)
        w-len (count w)]
    (loop [j 0 tab tab row 0]
      (if (= w-len j)
        tab
        (let [ch (nth w j)
              terminal? (= (inc j) w-len)
              key [row j ch]
              looked-up-row (get tab key)]
          (if (nil? looked-up-row)
            (recur (inc j)
                   (assoc! tab key [i terminal?])
                   i)
            (recur (inc j)
                   tab
                   row)))))))

(defn make-prefix-tree
  "Make a prefix tree from sorted word list"
  [words]
  (let [tab (transient (hash-map))]
    (persistent!
     (reduce #(add-word words %1 %2)
             tab
             (range (count words))))))

(defn lookup
  "Lookup tree by one character"
  [tree row offset ch]
  (get tree [row offset ch]))
