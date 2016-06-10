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
	
	'#authorizeLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/transfersAwaitingAuthorization";
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
	},
	
	'#paymentsAwaitingFeedbackLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/searchPaymentsAwaitingFeedback";
		}
	},
	
	'#pendingContractsLink': function(a) {
        setPointer(a);
        a.onclick = function() {
            self.location = pathPrefix + "/listBrokerCommissionContracts";
        }
    }

});