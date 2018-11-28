;Experimentation with the planner to figure out how it works

;DOES NOT WORK, CANT RESOLVE mfind in search/planner.clj

(require 'search.planner)

(def world-state
  '#{(user Usr1)
     (user Usr2)
     (user Usr3)
     (lift Lift)})

(def start-state
  '#{(user Usr1)
     (user Usr2)
     (user Usr3)
     (lift Lift)

     (at Lift Flr2)
     (on Usr1 Flr1)
     (on Usr2 Flr3)
     (on Usr3 Flr3)})

(def ops-alt

  {:name enter-lift
   :achieves ()
   :when   ( (at ?l ?f)
             (on ?u ?f) )
   :post ()
   :pre ()
   :del ( (on ?u ?f) )
   :add ( (in ?u ?l) )
   :cmd ( enter ?u )
   :txt (?u entered ?l on ?f)
   }

  { :name exit-lift
   :achieves ()
   :when   ( (at ?l ?f)
             (in ?u ?l) )
   :post ()
   :pre ()
   :del ( (in ?u ?l) )
   :add ((on ?u ?f) )
   :cmd (exit ?u)
   :txt (?u left ?l on ?f)
   }

  { :name call-lift
   :achieves ()
   :when   ( (on ?u ?f1)
             (at ?l ?f2)
             (:not (called ?f1)) )
   :post ()
   :pre ()
   :del ()
   :add ((called ?f1) )
   :cmd (call ?f1)
   :txt (called ?l from ?f1)
   }

  { :name select-floor
   :achieves ()
   :when   ( (in ?u ?l)
             (:not (called ?f)) )
   :post ()
   :pre ()
   :del ()
   :add ((called ?f) )
   :cmd (selected ?f)
   :txt (selected ?f in ?l)
   }

  { :name wait-call
   :achieves ()
   :when   ( (called ?f2)
             (at ?l ?f1)
             (on ?u ?f2) )
   :post ()
   :pre ()
   :del ((at ?l ?f1)
          (called ?f2))
   :add ((at ?l ?f2) )
   :cmd (move ?f2)
   :txt (?l call response on ?f2 by ?u)
   }
  { :name wait-select
   :achieves ()
   :when   ((called ?f2)
             (at ?l ?f1)
             (in ?u ?l) )
   :post ()
   :pre ()
   :del ((at ?l ?f1)
          (called ?f2))
   :add ((at ?l ?f2) )
   :cmd (move ?f2)
   :txt (?l select response to ?f1 by ?u)
   }


  )

(def ops
  '{enter-lift {:pre ((at ?l ?f)
                       (on ?u ?f))
                :add ((in ?u ?l))
                :del ((on ?u ?f))
                :txt (?u entered ?l on ?f)
                :cmd [enter ?u]}

    exit-lift {:pre ((at ?l ?f)
                      (in ?u ?l))
               :add ((on ?u ?f))
               :del ((in ?u ?l))
               :txt (?u left ?l on ?f)
               :cmd [exit ?u]}

    call-lift {:pre ()
               :add ((called ?f1))
               :del (())
               :txt (called ?l from ?f1)
               :cmd [call ?f1]}

    select-floor {:pre ((in ?u ?l)
                         (:not (called ?f)))
                  :add ((called ?f))
                  :del (())
                  :txt (selected ?f in ?l)
                  :cmd [select ?f]}

    wait-call {:pre ((called ?f2)
                      (at ?l ?f1)
                      (on ?u ?f2))
               :add ((at ?l ?f2))
               :del ((at ?l ?f1)
                      (called ?f2))
               :txt (?l call response on ?f2 by ?u)
               :cmd [move ?f2]}

    wait-select {:pre ((called ?f2)
                        (at ?l ?f1)
                        (in ?u ?l))
                 :add ((at ?l ?f2))
                 :del ((at ?l ?f1)
                        (called ?f2))
                 :txt (?l select response to ?f1 by ?u)
                 :cmd [move ?f2]}})

;(planner start-state '((on Usr1 Flr3) (on Usr2 Flr1)) ops-alt)