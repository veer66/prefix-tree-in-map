(ns prefix-tree-in-map.core)

(defn add-member [tab member i has-payload? payload]
  (let [member-len (count member)]
    (loop [j 0 tab tab row 0]
      (if (= member-len j)
        tab
        (let [element (nth member j)
              terminal? (= (inc j) member-len)
              key [row j element]
              looked-up-row (get tab key)]
          (if (nil? looked-up-row)
            (recur (inc j)
                   (assoc! tab key
                           (if has-payload?
                             [i terminal? payload]
                             [i terminal?]))
                   i)
            (recur (inc j)
                   tab
                   (first looked-up-row))))))))

(defn make-prefix-tree-with-payloads
  "Make a prefix tree from sorted word list with payloads"
  [members-with-payloads]
  (let [tab (transient (hash-map))]
    (persistent!
     (reduce #(add-member %1
                          (first %2)
                          (second %2)
                          true
                          (nth %2 2))
             tab
             (map vector
                  (map first members-with-payloads)
                  (range (count members-with-payloads))
                  (map second members-with-payloads))))))

(defn make-prefix-tree
  "Make a prefix tree from sorted word list"
  [members]
  (let [tab (transient (hash-map))]
    (persistent!
     (reduce #(add-member %1 (first %2) (second %2) false nil)
             tab
             (map vector members (range (count members)))))))

(defn lookup
  "Lookup tree by one character"
  [tree row offset element]
  (get tree [row offset element]))
