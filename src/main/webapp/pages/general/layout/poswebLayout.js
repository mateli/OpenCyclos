Behaviour.register({
	
	'#loginDataBar': function(el) {
		if (self.location.href.indexOf("/posweb/") >= 0) {
			el.addClassName('loginDataBarNoTop');
		}
	},
	
	'#receivePaymentButton': function(input) {
		input.onclick = function(event) {
			self.location = context + "/do/posweb/receivePayment";
		}
	},
	
	'#makePaymentButton': function(input) {
		input.onclick = function(event) {
			self.location = context + "/do/posweb/makePayment";
		}
	},
	
	'#searchTransactionsButton': function(input) {
        input.onclick = function(event) {
            self.location = context + "/do/posweb/searchTransactions";
        }
    },
	
	'#logoutButton': function(input) {
		input.onclick = function(event) {
			self.location = context + "/do/posweb/logout";
		}
	}, 
	
	'#printSettingsLink': function(link) {
	    setPointer(link);
        link.onclick = function(event) {
            self.location = context + "/do/posweb/printSettings";
        }
    }
});

Event.observe(self, "load", function() {
	var receive = $('receivePaymentButton');
	if (receive) {
		keyBinding(Event.KEY_F2, receive.onclick);
	}
	var make = $('makePaymentButton');
	if (make) {
		keyBinding(Event.KEY_F3, make.onclick);
	}
	var logout = $('logoutButton');
	if (logout) {
		keyBinding(Event.KEY_F10, logout.onclick);
	}
});