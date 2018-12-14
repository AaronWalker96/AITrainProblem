;Adam modified Y2 example of "train"ing

(def world-state
  '#{(cargo Cargo1)
     (cargo Cargo2)
     (cargo Cargo3)
     (train Train)})

(def start-state
  '#{(at Train Stn2)
     (on Cargo1 Stn1)
     (on Cargo2 Stn3)
     (on Cargo3 Stn3)})

(def ops
  '{enter-train {:pre ((at ?t ?s)
                        (on ?c ?s))
                 :add ((in ?c ?t))
                 :del ((on ?c ?s))
                 :txt (?c entered ?t on ?s)
                 :cmd [enter ?c]}

    exit-train {:pre ((at ?t ?s)
                       (in ?c ?t))
                :add ((on ?c ?s))
                :del ((in ?c ?t))
                :txt (?c left ?t on ?s)
                :cmd [exit ?c]}

    call-train {:pre ((on ?c ?s1)
                       (at ?t ?s2)
                       (:not (called ?s1)))
                :add ((called ?s1))
                :del (())
                :txt (called ?t from ?s1)
                :cmd [call ?s1]}

    select-station {:pre ((in ?c ?t)
                           (:not (called ?s)))
                    :add ((called ?s))
                    :del (())
                    :txt (selected ?s in ?t)
                    :cmd [select ?s]}

    wait-call {:pre ((called ?s2)
                      (at ?t ?s1)
                      (on ?c ?s2))
               :add ((at ?t ?s2))
               :del ((at ?t ?s1)
                      (called ?s2))
               :txt (?t call response on ?s2 by ?c)
               :cmd [move ?s2]}

    wait-select {:pre ((called ?s2)
                        (at ?t ?s1)
                        (in ?c ?t))
                 :add ((at ?t ?s2))
                 :del ((at ?t ?s1)
                        (called ?s2))
                 :txt (?t select response to ?s1 by ?c)
                 :cmd [move ?s2]}})

;(ops-search start-state '((on Cargo1 Stn3) (on Cargo2 Stn1)) ops :world world-state)
