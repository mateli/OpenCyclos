function updateWhen() {
	var isAlways = getValue(this) == "ALWAYS";
	var countText = "countText_" + this.getAttribute("commissionId");
	Element[isAlways ? "hide" : "show"](countText);
}

Behaviour.register({
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
		
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, keepDisabled);
		}
	},
	
	'select.when': function(select) {
		select.onchange = updateWhen;
		select.onchange();
	}	

});