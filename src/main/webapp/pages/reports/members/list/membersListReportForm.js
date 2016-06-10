function switchPeriodType() {
	if (getValue('membersListReport(periodType)') == 'PERIOD_CURRENT') {
		$('periodDate').value = "";
		disableField($('periodDate'));
		enableField($('accountsUpperCredits'));
		enableField($('accountsCredits'));
	}
	else {
		enableField($('periodDate'));
		$('periodDate').focus();
		disableField($('accountsUpperCredits'));
		disableField($('accountsCredits'));
	}
}

Behaviour.register({
	'.periodType': function(radio) {
		radio.onclick = switchPeriodType;
	},
	
	'#brokerUsername': function(input) {
		var div = $('brokersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, brokers:true}, 'brokerId', 'brokerUsername', 'brokerName', 'memberName');
	},

	'#brokerName': function(input) {
		var div = $('brokersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, brokers:true}, 'brokerId', 'brokerUsername', 'brokerName', 'memberName');
	},
	
	'#printReportButton': function(button) {
		button.onclick = function() {
			if ($('periodDate').value == "" && $('periodTypeHistory').checked) {
				alert(historyDateEmpty);
				return false;
			}
			var form = document.forms[0];
			printResults(form, pathPrefix + "/membersListReport");
		}
	},
	
	'#downloadReportButton': function(button) {
		button.onclick = function() {
			if ($('periodDate').value == "" && $('periodTypeHistory').checked) {
				alert(historyDateEmpty);
				return false;
			}
			var form = document.forms[0];
			submitTo(form, pathPrefix + "/membersListReportCsv");
		}
	},
	
	'#selectAllButton': function(button) {
		button.onclick = function() {
			$$('.checkbox').each(function(check) {
				check.checked = true;
			});
		}
	},
	
	'#selectNoneButton': function(button) {
		button.onclick = function() {
			$$('.checkbox').each(function(check) {
				check.checked = false;
			});
		}
	}
	
});

Event.observe(self, "load", function() {
	switchPeriodType();
});