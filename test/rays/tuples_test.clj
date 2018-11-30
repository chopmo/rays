(ns rays.tuples-test
  (:require [clojure.test :refer [testing deftest is]]
            [rays.tuples :as sut]))


(deftest test-tuples
  (testing "A tuple with w=1.0 is a point"
    (let [a {:x 4.3 :y -4.2 :z 3.1 :w 1.0}]
      (is (sut/point? a))
      (is (not (sut/vector? a)))))

  (testing "A tuple with w=0 is a vector"
    (let [a {:x 4.3 :y -4.2 :z 3.1 :w 0}]
      (is (not (sut/point? a)))
      (is (sut/vector? a))))

  (testing "->tuple creates a tuple"
    (is (= {:x 1 :y 2 :z 3 :w 0}
           (sut/->tuple 1 2 3 0))))

  (testing "->point creates tuples with w=1"
    (is (= (sut/->tuple 4 -4 3 1)
           (sut/->point 4 -4 3))))

  (testing "->vector creates tuples with w=0"
    (is (= (sut/->tuple 4 -4 3 0)
           (sut/->vector 4 -4 3))))

  (testing "equal?"
    (is (sut/equal? (sut/->tuple 1 2 3 0)
                    (sut/->tuple 1 2 3 0)))
    (is (not (sut/equal? (sut/->tuple 1 2 3 0)
                         (sut/->tuple 1 2 2 0)))))

  (testing "adding two tuples"
    (let [a1 (sut/->tuple 3 -2 5 1)
          a2 (sut/->tuple -2 3 1 0)]
      (is (sut/equal? (sut/->tuple 1 1 6 1)
                      (sut/add a1 a2)))))

  (testing "subtracting two points"
    (let [p1 (sut/->point 3 2 1)
          p2 (sut/->point 5 6 7)]
      (is (sut/equal? (sut/->vector -2 -4 -6)
                      (sut/subtract p1 p2)))))

  (testing "subtracting a vector from a point"
    (let [p (sut/->point 3 2 1)
          v (sut/->vector 5 6 7)]
      (is (sut/equal? (sut/->point -2 -4 -6)
                      (sut/subtract p v))))))
