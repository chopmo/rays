(ns rays.lights-test
  (:require [clojure.test :refer [deftest testing is]]
            [rays.colors :as c]
            [rays.tuples :as t]
            [rays.lights :as sut]))

(deftest test-lights
  (testing "a point light has a position and intensity"
    (let [intensity (c/->color 1 1 1)
          position (t/->point 0 0 0)
          light (sut/point-light position intensity)]
      (is (= intensity (sut/intensity light)))
      (is (= position (sut/position light))))
    )
  )
