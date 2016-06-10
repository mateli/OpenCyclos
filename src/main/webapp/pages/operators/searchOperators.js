Behaviour.register({
	'#newOperatorGroup': function(select) {
		select.onchange = function() {
			var groupId = this.value;
			if (!isEmpty(groupId)) {
				self.location = pathPrefix + "/createOperator?groupId=" + groupId
			}
		}
	}
});

Event.observe(self, "load", function() {
	getObject("query(keywords)").focus();
});