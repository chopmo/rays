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
      (is (sut/vector? a)))))
