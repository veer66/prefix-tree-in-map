(ns prefix-tree-in-map.core-test
  (:require [clojure.test :refer :all]
            [prefix-tree-in-map.core :refer :all]))

(deftest create-and-lookup-test
  (testing "add 1 char and lookup"
    (is (= (let [tree (make-prefix-tree ["A"])]
             (lookup tree 0 0 \A))
           [0 true])))
  (testing "add 1 char with payload and lookup"
    (is (= (let [tree (make-prefix-tree-with-payloads [["A" 4711]])]
             (lookup tree 0 0 \A))
           [0 true 4711])))
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

(deftest list-of-phrases-test
  (testing "basic"
    (is (= (let [tree (make-prefix-tree [["I" "saw" "it"]])]
             (lookup tree 0 0 "I"))
           [0 false]))))


(deftest more-rows
  (testing "two rows"
    (is (= (let [tree (make-prefix-tree [["A" "B"]
                                         ["B" "C"]])]
             (lookup tree 1 1 "C"))
           [1 true])))
  (testing "three rows"
    (is (= (let [tree (make-prefix-tree [["A" "B"]
                                         ["B" "C"]
                                         ["B" "K" "Foo"]])]
             (lookup tree 1 1 "K"))
           [2 false])))
  (testing "more"
    (is (= (let [tree (make-prefix-tree [["A"]
                                         ["B"]
                                         ["C" "X"]
                                         ["C" "Y" "Z"]
                                         ["D"]])]
             (lookup tree 3 2 "Z"))
           [3 true]))))

(run-tests)
