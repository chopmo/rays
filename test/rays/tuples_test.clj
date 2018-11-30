(ns rays.tuples-test
  (:require [clojure.test :refer [testing deftest is]]
            [rays.tuples :as sut]))


(deftest test-tuples
  (testing "A tuple with w=1.0 is a point"
    (let [a (sut/->tuple 4.3 -4.2 3.1 1.0)]
      (is (sut/point? a))
      (is (not (sut/vector? a)))))

  (testing "A tuple with w=0 is a vector"
    (let [a (sut/->tuple 4.3 -4.2 3.1 0)]
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
                      (sut/subtract p v)))))

  (testing "subtracting two vectors"
    (let [v1 (sut/->vector 3 2 1)
          v2 (sut/->vector 5 6 7)]
      (is (sut/equal? (sut/->vector -2 -4 -6)
                      (sut/subtract v1 v2)))))

  (testing "subtracting a vector from the zero vector"
    (let [zero (sut/->vector 0 0 0)
          v (sut/->vector 1 -2 3)]
      (is (sut/equal? (sut/->vector -1 2 -3)
                      (sut/subtract zero v)))))

  (testing "negating a tuple"
    (let [a (sut/->tuple 1 -2 3 -4)]
      (is (sut/equal? (sut/->tuple -1 2 -3 4)
                      (sut/negate a)))))

  (testing "multiplying a tuple by a scalar"
    (let [a (sut/->tuple 1 -2 3 -4)]
      (is (sut/equal? (sut/->tuple 3.5 -7 10.5 -14)
                      (sut/multiply a 3.5)))))

  (testing "multiplying a tuple by a fraction"
    (let [a (sut/->tuple 1 -2 3 -4)]
      (is (sut/equal? (sut/->tuple 0.5, -1, 1.5, -2)
                      (sut/multiply a 0.5))))))
