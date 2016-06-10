function switchTimePointType() {
    var notInTime = [$('trInvoices'), $('trLoans'), $('trReferences')];
	if (getValue('currentStateReport(timePointType)') == 'TIME_POINT_CURRENT') {
		setValue('timePoint', '');
		disableField('timePoint');
		notInTime.each(Element.show);
	} else {
		enableField('timePoint');
		setFocus('timePoint');
        notInTime.each(function(tr) {
            tr.hide();
            $A(tr.getElementsByTagName("input")).each(function(checkbox) {
                checkbox.checked = false;
            })
        });
	}
}

Behaviour.register({
	'.timePointType': function(radio) {
		radio.onclick = switchTimePointType;
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
	switchTimePointType();
});