Behaviour.register({
	'img.details': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/loanDetails?loanGroupId=" + getValue("loanGroupId") + "&memberId=" + getValue("memberId") + "&loanId=" + img.getAttribute("loanId");
		}
	},
	
	'img.exportCSV': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			submitTo(form, pathPrefix + "/exportLoansToCsv");
		}
	},

	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			printResults(form, pathPrefix + "/printLoans");
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			var memberId = parseInt(getValue('memberId'), 10);
			var loanGroupId = parseInt(getValue('loanGroupId'), 10);
			if (!isNaN(memberId) && memberId > 0) {
				self.location = pathPrefix + "/profile?memberId=" + memberId;
			} else if (!isNaN(loanGroupId) && loanGroupId > 0) {
				self.location = pathPrefix + "/editLoanGroup?loanGroupId=" + loanGroupId;
			} else {
				history.back();
			}
		}
	},
	
	'input.statusRadio': function(radio) {
		radio.onclick = function() {
			radio.form.submit();
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