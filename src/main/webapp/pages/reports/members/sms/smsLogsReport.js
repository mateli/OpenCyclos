function updateTypes() {
	var type = getValue('typeSelect');
	$$('.smsLogType').each(Element.hide);
	if (!isEmpty(type)) {
		$$('.smsLogType-' + type).each(Element.show);
		switch (type) {
			case 'NOTIFICATION':
				messageTypes.render();
				break;
			case 'MAILING':
				mailingTypes.render();
				break;
			case 'SMS_OPERATION':
				smsTypes.render();
				break;
		}
	}
}

Behaviour.register({

	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			printResults(form, pathPrefix + "/membersSmsLogsReportPrint");
		}
	},
	
	'img.exportCSV': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			submitTo(form, pathPrefix + "/membersSmsLogsReportCsv");
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true}, 'memberId', 'memberUsername', 'memberName', 'query(status)');
	},

	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true}, 'memberId', 'memberUsername', 'memberName', 'query(status)');
	},
	
	'#typeSelect': function(select) {
		select.onchange = updateTypes;
	}
	
});

Event.observe(self, 'load', updateTypes);