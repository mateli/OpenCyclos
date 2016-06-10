Behaviour.register({
	
	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			printResults(form, pathPrefix + "/printScheduledPaymentsSearch");
		}
	},
	
	'img.view': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/viewScheduledPayment?paymentId=" + img.getAttribute("paymentId");
		}
	},
	
	'input.searchType': function(radio) {
		radio.onclick = function() {
			radio.form.submit();
		}
	},

	'#accountSelect': function(select) {
		select.onchange = function() {
			select.form.submit();
		}
	},

	'#statusSelect': function(select) {
		select.onchange = function() {
			select.form.submit();
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + getValue("memberId");
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'memberId', 'memberUsername', 'memberName');
	}
});