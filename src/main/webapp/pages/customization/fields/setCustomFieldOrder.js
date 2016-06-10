Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			var params = $H();
			params.set('nature', getValue('nature'));
			switch (getValue('nature')) {
				case 'MEMBER_RECORD':
					params.set('memberRecordTypeId', getValue('memberRecordTypeId'));
					break;
				case 'PAYMENT':
					params.set('transferTypeId', getValue('transferTypeId'));
					params.set('accountTypeId', getValue('accountTypeId'));
					break;
			}
			backToLastLocation(params);
		}
	},
	
	'.draggableList': function(list) {
		Sortable.create(list);
	}
});