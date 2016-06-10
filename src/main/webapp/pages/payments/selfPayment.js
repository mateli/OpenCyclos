Behaviour.register({
	'#currencySelect': function(select) {
		select.onchange = function() {
			updateTransferTypes();
			setFocus("amount");
		}
	}, 
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + getValue("from");
		}
	}
});


Event.observe(self, "load", function() {
	updateTransferTypes();
	getObject("amount").focus();
});

selfPayment = true;