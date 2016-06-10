Behaviour.register({
	'img.details': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/loanDetails?backToSearchLoanPayments=true&loanId=" + img.getAttribute("loanId");
		}
	},

	'img.exportCSV': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			submitTo(form, pathPrefix + "/exportLoanPaymentsToCsv");
		}
	},

	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			printResults(form, pathPrefix + "/printLoanPayments");
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#brokerUsername': function(input) {
		var div = $('brokersByUsername');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"username", brokers:true}, 'brokerId', 'brokerUsername', 'brokerName');
	},
	
	'#brokerName': function(input) {
		var div = $('brokersByName');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"name", brokers:true}, 'brokerId', 'brokerUsername', 'brokerName');
	},
	
	'#transferTypeSelect': function(select) {
		select.onchange = function() {
			select.form.submit();
		}
	}
});