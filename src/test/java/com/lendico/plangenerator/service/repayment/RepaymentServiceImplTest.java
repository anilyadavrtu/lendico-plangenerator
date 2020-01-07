package com.lendico.plangenerator.service.repayment;

import com.lendico.plangenerator.domainobject.Repayment;
import com.lendico.plangenerator.domainobject.RepaymentPlan;
import com.lendico.plangenerator.service.annuity.AnnuityCalculator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link RepaymentServiceImpl}.
 * 
 * @author anil
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepaymentServiceImplTest {
	private RepaymentService service;

	@Before
	public void setUp() throws Exception {

		/* Dummy values for mocking. */
		BigDecimal loanAmount = new BigDecimal(89000.0);
		Double nominalRate = 5.0;
		int duration = 24;
		BigDecimal initialOutstandingPrincipal = new BigDecimal(5000);
		BigDecimal interest = new BigDecimal(20.83);
		BigDecimal annuity = new BigDecimal(219.36);
		BigDecimal principal = new BigDecimal(198.53);
		
		AnnuityCalculator annuityMock = mock(AnnuityCalculator.class);
		when(annuityMock.calculate(loanAmount, nominalRate, duration))
				.thenReturn(new BigDecimal(219.36));
		
		PaymentCalculator repaymentMock = mock(PaymentCalculator.class);
		when(repaymentMock.calculateInterest(nominalRate,
				initialOutstandingPrincipal))
						.thenReturn(new BigDecimal(20.83));
		when(repaymentMock.calculatePrincipal(interest, annuity))
				.thenReturn(new BigDecimal(198.53));
		when(repaymentMock.calculateRemainingOutstandingPrincipal(
				initialOutstandingPrincipal, principal))
						.thenReturn(new BigDecimal(4801.47));

		service = new RepaymentServiceImpl(annuityMock, repaymentMock);
	}

	@Test
	public void shouldGeneratePlan() {
		BigDecimal loanAmount = new BigDecimal(5000.0);
		Double nominalRate = 5.0;
		int duration = 24;
		LocalDateTime startDate = LocalDateTime.of(2018, 1, 1, 0, 0, 1);
		
		RepaymentPlan plan = service.generatePlan(loanAmount, nominalRate,
				duration, startDate);
		Assert.assertNotNull(plan);
		
		List<Repayment> repayments = plan.getRepayments();
		Assert.assertEquals(duration, repayments.size());
	}

	@Test
	public void shouldGenerateRepayment() {
		BigDecimal loanAmount = new BigDecimal(5000.0);
		Double nominalRate = 5.0;
		int duration = 24;
		LocalDateTime startDate = LocalDateTime.of(2018, 1, 1, 0, 0, 1);
		BigDecimal initialOutstandingPrincipal = new BigDecimal(5000);
		int monthsAfterStart = 0;
		
		Repayment repayment = service.generateRepayment(loanAmount, nominalRate,
				duration, startDate, initialOutstandingPrincipal,
				monthsAfterStart );
		Assert.assertNotNull(repayment);
	}
}