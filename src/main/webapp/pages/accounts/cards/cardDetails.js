var operation;

function hideAll() {
	var passwordDiv = $('passwordDiv');	
	var tableDiv = $('tableDiv');
	var cardCodeDiv = $('cardCodeDiv');
	var cardCodeDiv2 = $('cardCodeDiv2');
	var submitDiv = $('submitDiv');
	submitDiv.hide();
	passwordDiv.hide();
	tableDiv.hide();
	cardCodeDiv.hide();	
	if (cardCodeDiv2) {
		cardCodeDiv2.hide();
	}
}

function submitWhenHasNoTransactionPassword(form) {
	form.action = pathPrefix + "/updateCard?operation=" + operation +"&cardId=" + cardId;
	form.submit();
}

Behaviour.register({
	
	'#cardForm': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},

	'#backButton': function(button) {
		button.onclick = function() {
			if (listOnly) {
				self.location = pathPrefix + "/searchCards?memberId=" + memberId;
			} else {
				self.location = pathPrefix + (isBroker ? "/searchCardsAsBroker" : "/searchCards");
			}
		}
	},
	
	'#blockCard': function(button) {
		button.onclick = function() {
			hideAll();
			operation = 'block';
			var passwordDiv = $('passwordDiv');	
			var tableDiv = $('tableDiv');
			var submitDiv = $('submitDiv');
			var legendId = $('legendId');
			legendId.innerHTML = actionBlock;
			if (usesTransactionPassword) {
				if (!(transactionPasswordPending || transactionPasswordBlocked)) {
					submitDiv.show();
					passwordDiv.show();
					tableDiv.show();
					setFocus("_transactionPassword");
				} else {
					tableDiv.show();
					passwordDiv.show();
				}
			} else {
				if (confirm(confirmBlockCard)) {
					submitWhenHasNoTransactionPassword(this.form);
				}
				return false;
			}
		}
	},
	
	'#unblockCard': function(button) {
		button.onclick = function() {
			hideAll();
			operation = 'unblock';
			var passwordDiv = $('passwordDiv');	
			var tableDiv = $('tableDiv');
			var submitDiv = $('submitDiv');
			var legendId = $('legendId');
			legendId.innerHTML = actionUnblock;			
			if (usesTransactionPassword) {
				if (!(transactionPasswordPending || transactionPasswordBlocked)) {
					submitDiv.show();
					passwordDiv.show();	
					tableDiv.show();
					setFocus("_transactionPassword");
				} else {
					tableDiv.show();
					passwordDiv.show();
				}
			} else {
				if (confirm(confirmUnblockCard)) {
					submitWhenHasNoTransactionPassword(this.form);
				}
				return false;
			}
		}
	},
	
	'#unblockSecurityCode': function(button) {
		button.onclick = function() {
			hideAll();
			operation = 'unblockSecurityCode';
			var passwordDiv = $('passwordDiv');	
			var tableDiv = $('tableDiv');
			var submitDiv = $('submitDiv');
			var legendId = $('legendId');
			legendId.innerHTML = actionUnblockSecCode;			
			if (usesTransactionPassword) {
				if (!(transactionPasswordPending || transactionPasswordBlocked)) {
					submitDiv.show();
					passwordDiv.show();	
					tableDiv.show();
					setFocus("_transactionPassword");
				} else {
					tableDiv.show();
					passwordDiv.show();
				}
			} else {
				if (confirm(confirmUnblockSecurityCode)) {
					submitWhenHasNoTransactionPassword(this.form);
				}
				return false;
			}
		}
	},
	
	'#activateCard': function(button) {
		button.onclick = function() {
			hideAll();
			operation = 'activate';
			var passwordDiv = $('passwordDiv');	
			var tableDiv = $('tableDiv');
			var submitDiv = $('submitDiv');
			var legendId = $('legendId');
			var cardCodeDiv = $('cardCodeDiv');
			var cardCodeDiv2 = $('cardCodeDiv2');
			legendId.innerHTML = actionActivate;			
			if (usesTransactionPassword) {
				if (!(transactionPasswordPending || transactionPasswordBlocked)) {
					if (isManualCardCode) {
						submitDiv.show();
						passwordDiv.show();
						tableDiv.show();
						cardCodeDiv.show();
						if (cardCodeDiv2) {
							cardCodeDiv2.show();
						}
						setFocus("securityCode");
					} else {
						submitDiv.show();
						passwordDiv.show();
						tableDiv.show();
						setFocus("transactionPassword");
					}
				} else {
					tableDiv.show();
					passwordDiv.show();
				}	
			} else {
				if (isManualCardCode) {
					submitDiv.show();
					tableDiv.show();
					cardCodeDiv.show();
					if (cardCodeDiv2) {
						cardCodeDiv2.show();
					}
					setFocus("securityCode");
				} else {
					if (hasActiveCard) {
						var message = confirmActivateCard + "\n\n" + activateWarning;
						if (confirm(message)) {
							submitWhenHasNoTransactionPassword(this.form);
						}				
					} else {
						if (confirm(confirmActivateCard)) {
							submitWhenHasNoTransactionPassword(this.form);
						}
					}
				}
				return false;
			}
		}
	},
	
	'#cancelCard': function(button) {
		button.onclick = function() {
			hideAll();
			operation = 'cancel';
			var passwordDiv = $('passwordDiv');	
			var tableDiv = $('tableDiv');
			var submitDiv = $('submitDiv');
			var legendId = $('legendId');
			legendId.innerHTML = actionCancel;			
			if (usesTransactionPassword) {
				if (!(transactionPasswordPending || transactionPasswordBlocked)) {
					submitDiv.show();
					passwordDiv.show();
					tableDiv.show();
					setFocus("_transactionPassword");
				} else {
					tableDiv.show();
					passwordDiv.show();
				}
			} else {
				if (confirm(confirmCancelCard)) {
					submitWhenHasNoTransactionPassword(this.form);
				}
				return false;
			}	
		}
	},
	
	'#changeCardCode': function(button) {
		button.onclick = function() {
			hideAll();
			operation = 'changeCardCode';
			var passwordDiv = $('passwordDiv');	
			var tableDiv = $('tableDiv');
			var cardCodeDiv = $('cardCodeDiv');
			var cardCodeDiv2 = $('cardCodeDiv2');
			var submitDiv = $('submitDiv');
			var legendId = $('legendId');
			legendId.innerHTML = actionChangeCode;
			if (usesTransactionPassword) {
				if (!(transactionPasswordPending || transactionPasswordBlocked)) {
					submitDiv.show();
					passwordDiv.show();
					tableDiv.show();
					cardCodeDiv.show();
					if (cardCodeDiv2) {
						cardCodeDiv2.show();
					}
					setFocus("securityCode");
				} else {
					tableDiv.show();
					passwordDiv.show();
				}
			} else {
				submitDiv.show();
				tableDiv.show();
				cardCodeDiv.show();
				if (cardCodeDiv2) {
					cardCodeDiv2.show();
				}
				setFocus("securityCode");
			}							
		}
	},
	
	'#submitButton': function(button) {
		button.onclick = function() {
			var submit = true;
			var form = this.form;
			switch(operation) {
				case 'activate': {
					if (hasActiveCard) {
						var message = confirmActivateCard + "\n\n" + activateWarning;
						if (!confirm(message)) {
							submit = false;
						}				
					} else {
						if (!confirm(confirmActivateCard)) {
							submit = false;
						}
					}
					break;
				}
				case 'block': {
					if (!confirm(confirmBlockCard)) {
						submit = false;
					}
					break;
				}
				case 'unblock': {
					if (!confirm(confirmUnblockCard)) {
						submit = false;
					}
					break;
				}
				case 'unblockSecurityCode': {
					if (!confirm(confirmUnblockSecurityCode)) {
						submit = false;
					}
					break;
				}
				case 'cancel': {
					if (!confirm(confirmCancelCard)) {
						submit = false;
					}
					break;
				}
				case 'changeCardCode': {
					if (!confirm(confirmChangeCardCode)) {
						submit = false;
					}
					break;
				}
			}
			if (!submit) {
				return false;
			}
			form.action = pathPrefix + "/updateCard?operation=" + operation +"&cardId=" + cardId;			
		}
	}
});
