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
           (sut/->vector 4 -4 3)))))
