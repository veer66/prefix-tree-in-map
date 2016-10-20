(ns prefix-tree-in-map.core)

(defn add-member [members tab i]
  (let [member (nth members i)
        member-len (count member)]
    (loop [j 0 tab tab row 0]
      (if (= member-len j)
        tab
        (let [element (nth member j)
              terminal? (= (inc j) member-len)
              key [row j element]
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
  [members]
  (let [tab (transient (hash-map))]
    (persistent!
     (reduce #(add-member members %1 %2)
             tab
             (range (count members))))))

(defn lookup
  "Lookup tree by one character"
  [tree row offset element]
  (get tree [row offset element]))
