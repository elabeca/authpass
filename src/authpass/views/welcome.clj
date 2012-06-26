(ns authpass.views.welcome
  (:require [authpass.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage defpartial render]]
  		[hiccup.form]
        [hiccup.element]))

(defpage "/" []
         (common/layout
           [:p "Login"]))

(defpartial user-fields [{:keys [username password]}]
  (label "username" "Username: ")
  (text-field "username" username)
  (label "password" "Password: ")
  (password-field "password" password))

(defpage "/signup" _
  (common/layout
    (form-to [:post "/signupuser"]
            (user-fields nil)
            (submit-button "Sign up"))))

(def usernames-and-passwords (atom (hash-map)))

(defn hash-password [password] )

(defpage [:post "/signupuser"] posted-params
	(let [{:keys [username password]} posted-params]
		(swap! usernames-and-passwords #(assoc % username password))
  		(if (valid? username)
    		(common/layout
      			[:h1 (format "Thank you for registering, %s" username)]
      			[:ul
      				(for [[u p] @usernames-and-passwords] [:li u])]))))

(defpage "/login-form" _
	(common/layout
		(form-to [:post "/try-login"]
			(user-fields nil)
			(submit-button "Log in"))))

(defn valid-login? [username password]
	(= (@usernames-and-passwords username) password))

(defpage [:post "/try-login"] {:keys [username password]}
	(if (valid-login? username password)
		[:h1 (format "Welcome, %s" username)]
		[:h1 "Wrong un/pw, please try again."]))