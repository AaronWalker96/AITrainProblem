;; best first search mechanism;; based on earlier breadth-1st search
;; @args start start state
;; @args goal either a predicate to take a state determine if it is a goal
;;            or a state equal to the goal
;; @args LMG  legal move generator function which takes one state & returns
;;            a list of states (typically these new states will have a cost
;;            associated with them)
;; @args selector takes list of states & selects the next one to explore from
;; @args get-state removes cost information from a state returning the raw state
;; @args get-cost  removes other information from a state returning only the cost
;; @args debug prints some information


(defn A*search
  [start goal LMG & {:keys [get-state get-cost selector debug]
                     :or   {get-state :state
                            get-cost  :cost
                            selector  :undef
                            debug     false}}]
  (let [goal? (if (fn? goal)
                #(when (goal %) %)
                #(when (= % goal) %))
        member? (fn [lis x] (some (partial = x) lis))
        selector (if-not (= selector :undef)                ;it was a key arg
                   selector                                 ; se leave it as is, else set it as default
                   (fn [bag] (first (sort-by (comp get-cost first) bag))))]

    (loop [queued `((~start))
           visited nil]

      (if (empty? queued) nil             ;; fail if (null queued)
        (let [next (selector queued)      ;; select next node
              state (first next)          ;; filter out path
              raw-state (get-state state)
              ]                           ;; filter costs, etc

          (when debug (println 'selecting next '=> raw-state))
          (cond
            ;(and (fn? goal) (goal raw-state))     ;; is goal a predicate & goal found
            ;(reverse next)                        ;; quit with result

            (goal? raw-state)             ;; goal found
            (reverse next)                ;; quit with result

            :else
            (if (member? visited raw-state)
              (recur (remove #(= % next) queued) visited)
              (let [queued (remove #(= % next) queued)
                    moves (LMG state)
                    new-visited (cons raw-state visited)
                    new-states (map #(cons % next)
                                    (remove #(member? visited (get-state %))
                                            moves))]

                (when debug
                  (println 'exploring state '=> raw-state
                           'path next
                           'moves moves))


                (recur
                  (concat queued new-states)
                  new-visited)))))))))



;;====================== Map 1 ========================

(defn map1 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 'station-one) (list

                 {:state 'station-two, :cost (+ c 3)}


                 )
      (= n 'station-two) (list
                 {:state 'station-one, :cost (+ c 3)}

                 {:state 'station-three, :cost (+ c 3)}

                 )
      (= n 'station-three) (list
                 {:state 'station-two, :cost (+ c 3)}

                 )
      )
    )
  )

;;====================== Map 2 ========================

(defn map2 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n '1) (list

                 {:state '2, :cost (+ c 4.5)}

                 {:state '3, :cost (+ c 6)}

                 )
      (= n '2) (list
                 {:state '1, :cost (+ c 4.5)}

                 {:state '3, :cost (+ c 5.7)}

                 )
      (= n '3) (list
                 {:state '1, :cost (+ c 6)}

                 {:state '2, :cost (+ c 5.7)}

                 )
      )
    )
  )

;;====================== Map 3 ========================

(defn map3 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n '1) (list

                 {:state '2, :cost (+ c 4)}

                 {:state '3, :cost (+ c 2.8)}

                 {:state '4, :cost (+ c 4)}

                 )
      (= n '2) (list
                 {:state '1, :cost (+ c 4)}

                 {:state '3, :cost (+ c 2.8)}

                 {:state '5, :cost (+ c 4)}

                 )
      (= n '3) (list
                 {:state '1, :cost (+ c 2.8)}

                 {:state '2, :cost (+ c 2.8)}

                 {:state '4, :cost (+ c 2.8)}

                 {:state '5, :cost (+ c 2.8)}

                 )
      (= n '4) (list
                 {:state '1, :cost (+ c 4)}

                 {:state '3, :cost (+ c 2.8)}

                 {:state '5, :cost (+ c 4)}

                 )
      (= n '5) (list

                 {:state '2, :cost (+ c 4)}

                 {:state '3, :cost (+ c 2.8)}

                 {:state '4, :cost (+ c 4)}

                 )
      )
    )
  )

;;====================== Map 4 ========================

(defn map4 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n '1) (list

                 {:state '2, :cost (+ c 3.2)}

                 )
      (= n '2) (list
                 {:state '1, :cost (+ c 3.2)}

                 {:state '3, :cost (+ c 3.6)}

                 {:state '4, :cost (+ c 3.6)}

                 )
      (= n '3) (list
                 {:state '2, :cost (+ c 3.6)}

                 )
      (= n '4) (list
                 {:state '2, :cost (+ c 3.6)}

                 )
      )
    )
  )

;;====================== Map 5 ========================

(defn map5 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n '1) (list

                 {:state '2, :cost (+ c 2.8)}

                 )
      (= n '2) (list
                 {:state '1, :cost (+ c 2.8)}

                 {:state '3, :cost (+ c 3)}


                 )
      (= n '3) (list
                 {:state '2, :cost (+ c 3)}

                 {:state '4, :cost (+ c 3)}

                 )
      (= n '4) (list
                 {:state '3, :cost (+ c 3)}

                 {:state '5, :cost (+ c 2.8)}

                 )
      (= n '5) (list

                 {:state '4, :cost (+ c 2.8)}

                 )
      )
    )
  )

;;====================== Map 6 ========================

(defn map6 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n '1) (list

                 {:state '2, :cost (+ c 2)}

                 )
      (= n '2) (list
                 {:state '1, :cost (+ c 2)}

                 {:state '3, :cost (+ c 2)}


                 )
      (= n '3) (list
                 {:state '2, :cost (+ c 2)}

                 )
      (= n '4) (list
                 {:state '5, :cost (+ c 2)}

                 )
      (= n '5) (list

                 {:state '4, :cost (+ c 2)}

                 )
      )
    )
  )

;;====================== Map 7 ========================

(defn map7 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n '1) (list

                 {:state '1, :cost (+ c 4)}

                 {:state '2, :cost (+ c 4)}

                 )
      (= n '2) (list
                 {:state '1, :cost (+ c 4)}

                 {:state '3, :cost (+ c 3)}


                 )
      (= n '3) (list
                 {:state '2, :cost (+ c 3)}

                 {:state '4, :cost (+ c 3)}

                 )
      (= n '4) (list
                 {:state '3, :cost (+ c 3)}

                 )
      )
    )
  )

;;====================== Map 8 ========================

(defn map8 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n '1) (list

                 {:state '2, :cost (+ c 2.2)}

                 {:state '5, :cost (+ c 2.2)}

                 )
      (= n '2) (list
                 {:state '1, :cost (+ c 2.2)}

                 {:state '3, :cost (+ c 3.2)}


                 )
      (= n '3) (list
                 {:state '2, :cost (+ c 3.2)}

                 {:state '4, :cost (+ c 2.8)}

                 )
      (= n '4) (list
                 {:state '3, :cost (+ c 2.8)}

                 {:state '5, :cost (+ c 2)}

                 )
      (= n '5) (list
                 {:state '1, :cost (+ c 2.2)}

                 {:state '4, :cost (+ c 2)}

                 )
      )
    )
  )

;;====================== Map 9 ========================

(defn map9 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n '1) (list

                 {:state '2, :cost (+ c 4.8)}

                 )
      (= n '2) (list
                 {:state '1, :cost (+ c 4.8)}

                 {:state '3, :cost (+ c 2)}


                 )
      (= n '3) (list
                 {:state '2, :cost (+ c 2)}

                 {:state '4, :cost (+ c 2)}

                 )
      (= n '4) (list
                 {:state '3, :cost (+ c 2)}

                 {:state '5, :cost (+ c 2.2)}

                 {:state '6, :cost (+ c 2.8)}

                 )
      (= n '5) (list
                 {:state '4, :cost (+ c 2.2)}

                 )
      (= n '6) (list
                 {:state '4, :cost (+ c 2.8)}

                 {:state '7, :cost (+ c 3.2)}

                 )
      (= n '7) (list
                 {:state '6, :cost (+ c 3.2)}

                 {:state '8, :cost (+ c 5.1)}

                 )
      (= n '8) (list
                 {:state '7, :cost (+ c 5.1)}

                 {:state '9, :cost (+ c 3.2)}

                 {:state '10, :cost (+ c 2.8)}

                 )
      (= n '9) (list
                 {:state '8, :cost (+ c 3.2)}

                 )
      (= n '10) (list
                 {:state '8, :cost (+ c 2.8)}

                 {:state '11, :cost (+ c 2.8)}

                 {:state '12, :cost (+ c 3)}

                 )
      (= n '11) (list
                 {:state '10, :cost (+ c 2.8)}

                 )
      (= n '12) (list
                 {:state '10, :cost (+ c 3)}

                 )
      )
    )
  )

;;====================== Testing ========================


(defn a*lmg [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (list

      {:state (+ n 1), :cost (+ c 2)}

      {:state (+ n 5), :cost (+ c 7)}

      {:state (* n 2), :cost (+ c 8)}

      )))

(defn a*lmg-map [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 'a) (list

                 {:state 'b, :cost (+ c 12)}

                 {:state 'c, :cost (+ c 10)}

                 {:state 'e, :cost (+ c 9)}

                 )
      (= n 'b) (list
                 {:state 'a, :cost (+ c 13)}

                 {:state 'c, :cost (+ c 10)}

                 {:state 'd, :cost (+ c 8)}

                 )
      (= n 'c) (list

                 {:state 'a, :cost (+ c 12)}

                 {:state 'b, :cost (+ c 11)}

                 {:state 'd, :cost (+ c 8)}

                 {:state 'f, :cost (+ c 6)}

                 )
      (= n 'd) (list

                 {:state 'b, :cost (+ c 11)}

                 {:state 'h, :cost (+ c 6)}

                 {:state 'c, :cost (+ c 10)}

                 )
      (= n 'e) (list

                 {:state 'a, :cost (+ c 14)}

                 {:state 'f, :cost (+ c 5)}

                 {:state 'g, :cost (+ c 7)}

                 )
      (= n 'f) (list

                 {:state 'c, :cost (+ c 11)}

                 {:state 'e, :cost (+ c 9)}

                 {:state 'g, :cost (+ c 8)}

                 )
      (= n 'g) (list

                 {:state 'e, :cost (+ c 9)}

                 {:state 'f, :cost (+ c 9)}

                 {:state 'h, :cost (+ c 5)}

                 )
      (= n 'h) (list

                 {:state 'g, :cost (+ c 10)}

                 {:state 'd, :cost (+ c 12)}

                 )
      )
    )
  )

(defn Dijkstra [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 'a) (list

                 {:state 'b, :cost (+ c 3)}

                 {:state 'c, :cost (+ c 2)}

                 {:state 'e, :cost (+ c 4)}

                 )
      (= n 'b) (list
                 {:state 'a, :cost (+ c 3)}

                 {:state 'c, :cost (+ c 2)}

                 {:state 'd, :cost (+ c 2)}

                 )
      (= n 'c) (list

                 {:state 'a, :cost (+ c 2)}

                 {:state 'b, :cost (+ c 2)}

                 {:state 'd, :cost (+ c 2)}

                 {:state 'f, :cost (+ c 3)}

                 )
      (= n 'd) (list

                 {:state 'b, :cost (+ c 2)}

                 {:state 'h, :cost (+ c 6)}

                 {:state 'c, :cost (+ c 2)}

                 )
      (= n 'e) (list

                 {:state 'a, :cost (+ c 4)}

                 {:state 'f, :cost (+ c 2)}

                 {:state 'g, :cost (+ c 2)}

                 )
      (= n 'f) (list

                 {:state 'c, :cost (+ c 3)}

                 {:state 'e, :cost (+ c 2)}

                 {:state 'g, :cost (+ c 3)}

                 )
      (= n 'g) (list

                 {:state 'e, :cost (+ c 2)}

                 {:state 'f, :cost (+ c 3)}

                 {:state 'h, :cost (+ c 5)}

                 )
      (= n 'h) (list

                 {:state 'g, :cost (+ c 5)}

                 {:state 'd, :cost (+ c 6)}

                 )
      )
    )
  )

(defn test-A [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 's) (list

                 {:state 'a, :cost (+ c 11)}

                 {:state 'd, :cost (+ c 13)}

                 )
      (= n 'a) (list
                 {:state 'b, :cost (+ c 8)}

                 )
      (= n 'b) (list

                 {:state 'c, :cost (+ c 14)}

                 )
      (= n 'd) (list

                 {:state 'e, :cost (+ c 10)}

                 )
      (= n 'e) (list

                 {:state 'g, :cost (+ c 4)}

                 )
      (= n 'c) (list

                 {:state 'g, :cost (+ c 8)}

                 )
      )
    )
  )

(defn test-D [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 's) (list

                 {:state 'a, :cost (+ c 4)}

                 {:state 'e, :cost (+ c 5)}

                 {:state 'f, :cost (+ c 6.4)}

                 )
      (= n 'a) (list
                 {:state 'b, :cost (+ c 2.2)}

                 )
      (= n 'b) (list

                 {:state 'c, :cost (+ c 3.6)}

                 )
      (= n 'c) (list

                 {:state 'd, :cost (+ c 5)}

                 )
      (= n 'd) (list

                 {:state 'c, :cost (+ c 5)}

                 {:state 'end, :cost (+ c 5)}

                 )
      (= n 'e) (list

                 {:state 'g, :cost (+ c 2.8)}

                 )
      (= n 'f) (list

                 {:state 'g, :cost (+ c 2.8)}

                 {:state 'i, :cost (+ c 2.8)}

                 )
      (= n 'g) (list

                 {:state 'f, :cost (+ c 2.8)}

                 {:state 'h, :cost (+ c 3.6)}

                 )
      (= n 'h) (list

                 {:state 'end, :cost (+ c 4)}

                 )
      (= n 'i) (list

                 {:state 'd, :cost (+ c 2.8)}

                 )
      )
    )
  )

;;(A*search {:state 'a, :cost 0} (fn [x] (= x 'h)) a*lmg-map)


(defn remove-last [str]
  (.substring (java.lang.String. str) 0 (- (count str) 1))
  )

(defn stateAdapter [plannerOutput]
  (let [numMoves (count (clojure.string/split plannerOutput #"move"))
        count 2
        formatted (clojure.string/replace plannerOutput #"\)\(" "\\) \\(")
        ]
    (loop [x count end numMoves result []]
      (if (= x (+ numMoves 1)) result
      (when (<= x end)
        (def move (nth (clojure.string/split formatted #"move") (- x 1)))
        (def startState (nth (clojure.string/split move #" ") 3))
        (def goalState (nth (clojure.string/split move #" ") 2))
        (def startState (remove-last (apply str startState)))

        (def  temp (apply str  startState "," goalState))

        (recur (+ x 1) numMoves (conj result temp)))
      )
      )
    )
  )


;"((move train-one station-three station-one)(pickup cargo-one)(move train-one station-two station-three)(drop cargo-one))"

;((move train-one station-three station-one)(pickup cargo-one)(move train-one station-two station-three)(drop cargo-one)(move train-one station-four station-two)(pickup cargo-two)(move train-one station-two station-four)(drop cargo-two))

