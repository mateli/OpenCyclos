
function transferIdOf(element) {
	return element.id.split("_")[1];
}

function updateSelection(sel) {
	var checked = sel.checked;
	var id = transferIdOf(sel);
	var call = checked ? enableField : disableField;
	call($('id_' + id), true);
	call($('transfer_' + id), true);
	call($('loan_' + id), true);
	call($('date_' + id), true);
	call($('amount_' + id), true);
}

Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			var selection = ensureArray(getObject("selection"));
			var hasSelection = false;
			for (var i = 0, len = selection.length; i < len; i++) {
				var sel = selection[i];
				if (sel.type == 'hidden') {
					hasSelection = true;
					continue;
				} else if (!sel.checked) {
					continue;
				}
				var id = transferIdOf(sel);
				var action = actions.get(id);
				var amount = $("amount_" + id);
				var isPayment = ['GENERATE_SYSTEM_PAYMENT', 'GENERATE_MEMBER_PAYMENT'].include(action);
				if (isPayment && isEmpty(amount.value)) {
					alert(amountRequiredMessage);
					setFocus(amount);
					return false;
				}
				hasSelection = true;
			}
			if (!hasSelection) {
				alert(noneSelectedMessage);
				return false;
			}
			return confirm(processConfirmationMessage);
		}
	},
	
	'input.selection': function(checkbox) {
		checkbox.checked = false;
		checkbox.onclick = checkbox.onchange = function() {
			updateSelection(checkbox);
		}
	},
	
	'#selectAllButton': function(button) {
		button.onclick = function() {
			var selection = ensureArray(getObject("selection"));
			for (var i = 0, len = selection.length; i < len; i++) {
				var sel = selection[i];
				sel.checked = true;
				updateSelection(sel);
			}
		}
	},
	
	'#selectNoneButton': function(button) {
		button.onclick = function() {
			var selection = ensureArray(getObject("selection"));
			for (var i = 0, len = selection.length; i < len; i++) {
				var sel = selection[i];
				sel.checked = false;
				updateSelection(sel);
			}
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			history.back();
		}
	}
});

Event.observe(self, 'load', function() {
	$('selectNoneButton').onclick();
});