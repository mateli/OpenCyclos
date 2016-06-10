function updateAccountTypes(accountTypes) {
	setOptions('accountTypeSelect', accountTypes, true, false, "name", "id");
	$('accountTypeSelect').options[0].text = emptyAccountsMessage;
	clearOptions('initialCreditTransferTypeSelect');
	clearOptions('initialDebitTransferTypeSelect');
}

function updateInitialCreditTypes(transferTypes) {
	setOptions('initialCreditTransferTypeSelect', transferTypes, true, false, "name", "id");
	$('initialCreditTransferTypeSelect').options[0].text = emptyInitialCreditMessage;
}

function updateInitialDebitTypes(transferTypes) {
	setOptions('initialDebitTransferTypeSelect', transferTypes, emptyInitialDebitMessage, false, "name", "id");	
	$('initialDebitTransferTypeSelect').options[0].text = emptyInitialDebitMessage;
}

Behaviour.register({

	'#groupSelect': function(select) {
		select.onchange = function() {
			updateAccountTypes([]);
			var params = $H();
			params.set('memberGroupId', getValue(select));
			findAccountTypes(params, updateAccountTypes);
		}
	},

	'#accountTypeSelect': function(select) {
		select.onchange = function() {
			clearOptions('initialCreditTransferTypeSelect');
			clearOptions('initialDebitTransferTypeSelect');

			if (select.selectedIndex > 0) {
				var params = $H();
				params.set('context', 'AUTOMATIC');
				params.set('fromNature', 'SYSTEM');
				params.set('toAccountTypeId', getValue(select));
				findTransferTypes(params, updateInitialCreditTypes);
	
				var params = $H();
				params.set('context', 'AUTOMATIC');
				params.set('fromAccountTypeId', getValue(select));
				params.set('toNature', 'SYSTEM');
				findTransferTypes(params, updateInitialDebitTypes);
			}
		}
	},

	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	}

});
