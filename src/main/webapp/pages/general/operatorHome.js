Behaviour.register({
	'#messagesLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/searchMessages";
		}
	},
	
	'#invoicesLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/searchInvoices";
		}
	},
	
	'#loansLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/searchLoans";
		}
	},
	
	'#paymentsLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/accountOverview";
		}
	},
	
	'#referencesLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/references";
		}
	}

});

Event.observe(self, 'load', function() {
	try {
		getObject("to").focus();
	} catch (e) {}
});