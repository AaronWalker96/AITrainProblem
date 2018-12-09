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

      (if (empty? queued) nil                               ;; fail if (null queued)
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

;;(A*search {:state 'a, :cost 0} (fn [x] (= x 'e)) a*lmg-map)

(defn stateAdapter [plannerOutput]
  (let [startState (nth (clojure.string/split plannerOutput #",") 0)
        startState (clojure.string/replace startState #"StartState: " "")
        goalState (nth (clojure.string/split plannerOutput #",") 1)
        goalState (clojure.string/replace goalState #" GoalState: " "")
        ]

    (str "{:state '" startState ", :cost 0} (fn [x] (= x '" goalState "))")
  )
)



