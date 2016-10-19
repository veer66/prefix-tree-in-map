(ns prefix-tree-in-map.core-test
  (:require [clojure.test :refer :all]
            [prefix-tree-in-map.core :refer :all]))

(deftest create-and-lookup-test
  (testing "add 1 char and lookup"
    (is (= (let [tree (make-prefix-tree ["A"])]
             (lookup tree 0 0 \A))
           [0 true])))
  (testing "add 2 chars and lookup"
    (let [words ["AB"]
          tree (make-prefix-tree words)]
      (is (= (lookup tree 0 0 \A)
             [0 false]))
      (is (= (lookup tree 0 1 \B)
             [0 true]))))
  (testing "add 2 words"
    (let [words ["A" "AB"]
          tree (make-prefix-tree words)]
      (is (= (lookup tree 0 0 \A)
             [0 true]))
      (is (= (lookup tree 0 1 \B)
             [1 true])))))
      
(run-tests)
