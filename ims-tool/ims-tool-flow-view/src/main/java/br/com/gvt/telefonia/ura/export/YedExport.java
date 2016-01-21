package br.com.gvt.telefonia.ura.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import br.com.gvt.telefonia.add.model.Flow;
import br.com.gvt.telefonia.ura.diagram.model.Announce;
import br.com.gvt.telefonia.ura.diagram.model.Choice;
import br.com.gvt.telefonia.ura.diagram.model.Decision;
import br.com.gvt.telefonia.ura.diagram.model.DecisionGroup;
import br.com.gvt.telefonia.ura.diagram.model.Disconnect;
import br.com.gvt.telefonia.ura.diagram.model.Menu;
import br.com.gvt.telefonia.ura.diagram.model.NoMatchInput;
import br.com.gvt.telefonia.ura.diagram.model.Operation;
import br.com.gvt.telefonia.ura.diagram.model.PromptCollect;
import br.com.gvt.telefonia.ura.diagram.model.Transfer;
import br.com.gvt.telefonia.ura.util.Constantes;
import br.com.gvt.telefonia.ura.util.FluxElement;
import br.com.gvt.telefonia.ura.util.Retangulo;

public class YedExport implements IExport {
	
	private boolean isFirst = true;
	private String formFoco = "";
	private String formFocoType = "";

	private Map<String,String> map;
	private List<String> mapFlecha;
	private String header;
	private StringBuilder content = new StringBuilder();
	private String footer;
	private int isDecision;
	private int auxId;
	private double height = 500;

	private int nodeCounter = 0;
	private int edgeCounter = 0;
	
	public YedExport() {
		super();
		StringBuilder sbAux = new StringBuilder();

		sbAux.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
		sbAux.append("<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:y=\"http://www.yworks.com/xml/graphml\" xmlns:yed=\"http://www.yworks.com/xml/yed/3\" xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns http://www.yworks.com/xml/schema/graphml/1.1/ygraphml.xsd\">\n");
		sbAux.append("<key attr.name=\"Description\" attr.type=\"string\" for=\"graph\" id=\"d0\"/>\n");
		sbAux.append("<key for=\"port\" id=\"d1\" yfiles.type=\"portgraphics\"/>\n");
		sbAux.append("<key for=\"port\" id=\"d2\" yfiles.type=\"portgeometry\"/>\n");
		sbAux.append("<key for=\"port\" id=\"d3\" yfiles.type=\"portuserdata\"/>\n");
		sbAux.append("<key attr.name=\"url\" attr.type=\"string\" for=\"node\" id=\"d4\"/>\n");
		sbAux.append("<key attr.name=\"description\" attr.type=\"string\" for=\"node\" id=\"d5\"/>\n");
		sbAux.append(" <key for=\"node\" id=\"d6\" yfiles.type=\"nodegraphics\"/>\n");
		sbAux.append(" <key for=\"graphml\" id=\"d7\" yfiles.type=\"resources\"/>\n");
		sbAux.append("<key attr.name=\"url\" attr.type=\"string\" for=\"edge\" id=\"d8\"/>\n");
		sbAux.append(" <key attr.name=\"description\" attr.type=\"string\" for=\"edge\" id=\"d9\"/>\n");
		sbAux.append("<key for=\"edge\" id=\"d10\" yfiles.type=\"edgegraphics\"/>\n");
		sbAux.append("<graph edgedefault=\"directed\" id=\"G\">\n");

		this.header = sbAux.toString();
		
		sbAux.delete(0, sbAux.length());
		
		
		sbAux.append("\n </graph> \n");
		sbAux.append("<data key=\"d7\"> \n");
		sbAux.append("    <y:Resources> \n");
		sbAux.append("      <y:Resource id=\"1\" type=\"java.awt.image.BufferedImage\">iVBORw0KGgoAAAANSUhEUgAAAJAAAACQCAYAAADnRuK4AAB5M0lEQVR42uydBVzV2br+ORPq2CDd&#13;");
		sbAux.append("DRISgnQ3CCiChAooKCoh0lIS0iEhdnd3jTV2YLdjd3f381/vD5dnH8/cGe89ztxz/4ftZ7l/O9hs&#13;");
		sbAux.append("9vru533ed8VPDM2X5su/cBFr/giaL80ANV+aAWq+NAPUfGkGqPnSfGkGqPnSDFDzpRmg/08ub9++&#13;");
		sbAux.append("/Xz85s0bvH//Xjj++PEjnjx5Ihy/e/dOeEz0Z0Rv03OpiV7oZ549e/ZPz6H24cMHoYn+ri9/lj/W&#13;");
		sbAux.append("DND/gcvLly/x/Pnzz7fpmN+mawKGdzx1rGiHiwJIxwTd06dPhedyOOiYoHj9+rXwuwg+/hr0ehxI&#13;");
		sbAux.append("un716tXn39kM0P+Bi+g3nTr197751PkvXrz43OmPHz/+B9Wg+zhgvP13YKDnc4iaFej/yIWAEFUQ&#13;");
		sbAux.append("us3VgTqS1OPLEEVKcvbsWWzcuBHbt2/H5s2bsWHDBmzduhWNjY04cuQIDhw4gN27dwuvSY2rD1eg&#13;");
		sbAux.append("L8Fp9kD/hy/UuQSLqPcRDWkEFQGzYsUKjBw5EsHBwfDz80Pv3r2F4z59+qBfv34ICQlBz549hRYW&#13;");
		sbAux.append("Fobo6GhERUUhOzsbM2fOxL59+3Djxo3PqkSqxZWLhzAe+riqNQP0f+DCO/G3LhcvXsSkSZMEONzc&#13;");
		sbAux.append("3AQ4QkNDhdtBQUEIDw9HbGwscnNzUVVVhfr6etTW1qKyshKlpaXCYwQRPZ+AGzJkCMaPH48TJ078&#13;");
		sbAux.append("pvKIGuwvlaoZoH/zEMY7jzru119/xcqVK9G9e3dBbXx9feHq6gorKyt069YNzs7O6NGjBwICAoTb&#13;");
		sbAux.append("0tLSaNOmDZSVleHi4oKBAwciIyNDgGXChAmorq5GUlKSoFgEXkJCAsrLy3Ho0CHcunXrn0KaaJbW&#13;");
		sbAux.append("DND/0uXjp/Ze5PgfHmDt49t3ePHsOV6/bAphj58+wbqf1yMxJRluXp6wc7BHV3MzaGprQUNLExZW&#13;");
		sbAux.append("lvD284W7pwdM2P1RMYOQlpmFitE1qKqtQ0rGCHh294WSmjq+b9kKEhIS8PLwRHFxKSZPnspUqRyx&#13;");
		sbAux.append("Q4eh/4BBGDhoKNLSs7Bg4VJcvXbjC0/04VNrBuh/L8Oi2g6pDPkcDhL1zVv23+t3+PCcQfOedRKD&#13;");
		sbAux.append("596tm0KHTZ4+Da6+3nD0YYDYWkJZRx2txNtCQVMFKroaMLG2QGBEHyxZuwYnL13Ew9ev8OzDRxB+&#13;");
		sbAux.append("1Kjyc/nhE+w5eQbrt+9ETMwQ/CD2PTq27wQ3V29MnjQL8+evRFrGKPTpNxTDknMRPiAWWblFePGS&#13;");
		sbAux.append("h9EPePb0Id6+efEZoi+NPCnUX5Hq/8cD9OTd+6aOZf3w5MVr3j8Mmjd49/g5nt+9J0D09uUL5l1G&#13;");
		sbAux.append("w8LGGnbuLpDVVIWyviY0DXVhamWK9pIdYWpphrWb1oOhh5fvX4Ml7cIxXT959QIPnj0Rrt8yaXsn&#13;");
		sbAux.append("CBz7RR/e4fXDB0hPGA6pDp2goayFKRNnYeyYGUhNKcDw5DxED0pCasZIjCoqx6PHT5uytVdP2c+/&#13;");
		sbAux.append("Fd4sD2lfeqa/Inv7jw9hj169EdTn2Tv2rX719vMDr1jY4lHi4tlzKC8rgbe3J5Q1mNJoqUFGVQHy&#13;");
		sbAux.append("GsqQV1FA27atkZ6RTHYb79+9xPMn9/Hh9VP2Oi/x8cV9BiNrrx4yZWPt1QPg+S18uHcJr278ipvH&#13;");
		sbAux.append("9jG1e4Rnl8/i+M5tcGV+SaZde6xbugr11Q0YGpOA4YlpGJaQgkExsaitG4Mnz55+CrcfPqsN+SHR&#13;");
		sbAux.append("DK05hP1FAL1h/714+0FQBO6Fnjx5JqTudOP61WtoGFMHOzsbGBjoQVlVCSpqitAx0EWbti1hYWqE&#13;");
		sbAux.append("gzu2sRd6hed3buD1vWuMIxao7l9hL3yHNWaAn7HjxxeAh2cYsaw9Ye3ZWQYWu+8Ve+zVddZu4+3F&#13;");
		sbAux.append("Y3h0+iBCXO2g1akDls+cgdkTJ2FI1EDkjshBfPwwxLE2Z+584b2+ePP2H9Tmt4ZMmgH6CyB68vT5&#13;");
		sbAux.append("ZzP9+PkLoWPo+M6D+yitKoOjswMsLMyhqaUKJUVZSEl2gJyUBGIi+jI1eQw8uM1geIyPNy7iw5VT&#13;");
		sbAux.append("eH5iFy7+PA/zsodgyrBgjB/ojareFij01EaOgzxybKVR6CCLAlcljO3vgMIgMyzNj8atjTOBawfw&#13;");
		sbAux.append("8dxehNkZwUFHCT/PnY4JFaVIjh2CrPQMJCWnIi4xGes2bcWr9yLGX8T7kBrxGlIzQH8BQa9eNdVT&#13;");
		sbAux.append("Xr95hzuPHgnG+vaTh5g0exosnWxhbG4EGdlOkJJoBz0tFajKSGBEwlCmIiwc3WAqcuscg+gqzqyY&#13;");
		sbAux.append("iblZg5HhaYq87mbIctZCiacOxvTQx5y+XbFqkDU2xdril3gbbI+zwpYEG8wd0BUL4x1R5qeJ2mAT&#13;");
		sbAux.append("VIeZY2lOBHZPLkROiBt8jdVxcO1SVI/MRP6IdCH1H5GbjwGxibh655HwnkXTeT6uRqWGv6JOJPaf&#13;");
		sbAux.append("Dg+l6XRNnfDsxXM8ff0SF25ew7iZU+EfFoi2Uh3h5O4IcfGf0EJMDCoSrTHA34vFj0fAzfN4vm89&#13;");
		sbAux.append("dtRmItPdGBE67ZHnqotq/y6o62GImWGmmBlsiPnBulgSpotVoZpY11cDW/prY+9gAzQmmmLfCHus&#13;");
		sbAux.append("GGSIY2UBWBrTFYsHW2FciCEa+lljVC9z6P1NDLkRvti2ZCby0xJRXVWBtKxcxCRlYOailbjLMjoa&#13;");
		sbAux.append("1RfNuHi96q/wRP/xAL158bIJJJZpffiUHS1esxxuPbzR2cwA6p3V8LcfxKCrqQBzXWagW4khKyKQ&#13;");
		sbAux.append("maOzWJCbgAwHTSR37Yh0cwlM69MNY/20MakHa36qWBbRBSsj9LE2ojM2RGhjc4Q6tg1QR+NgHZxM&#13;");
		sbAux.append("MsLZLAscz+yG47k2WDdADftSLbAzxQr7R/rg51R3TI2yQU0/eygxcCfkxmPb8vlITYzHyMJiJOUU&#13;");
		sbAux.append("Ynh2AS5fv4V79+4Jmdn/xpjZfwRA9OHybyNVlmk6xWcDxO5/zW6TEpFxXrhsEVy83NBJUQYyytKQ&#13;");
		sbAux.append("VZCAmpIENGTaQLP932Au1Qq9DJQw1NYQafY6qHJTQb2bFMZ6ymKKryJm9lDGvEBVLAlRw9JQVawL&#13;");
		sbAux.append("18T6SDVsGaCBbQM1sXuINhpjdXAwQQ+HEnVxNLkzjqd1xsl0Q5zMMMXRDDPsT7fArjQ7bEpzw+aC&#13;");
		sbAux.append("MOQwRXPXaIf1MxswpqIY+QWjEJ+Wg6SsAlTVjsGDBw8++x3B/Den8d+w1sNCE59CQR8yHxQlkD68&#13;");
		sbAux.append("e4+7N28IEFGtZ8WKZegVFADNzhqQkZNES6Y2yvIdoC7dGvpSLWEt1xrd1ToiwkAOwy00UOiogQYX&#13;");
		sbAux.append("SUx2aYPpHu0w20cc8/2lsCRQFstD5FjIUsD6cBX8HKmMTVEqDCB17GIA7UvQxf5EBlBSZ5xI1cKp&#13;");
		sbAux.append("VE2cTdPFrxn6OJ2uz4AyxoEUM+xMtcXKBAcsTeuJgM7tUBrfF8tmTkRudg7Sc0dheGY+yqtrhVH9&#13;");
		sbAux.append("Lwdbm0PYNwSIfxtFZw7y2YRUSzlx5DCKRhXA3MwUP/zwHdq0bgF1FVnosqYt2w6dJX6ApUwLBOl2&#13;");
		sbAux.append("wmBTeSQYyyDLXAGjnZQwyaktZrj8iNnurTDfuy2W+ItjeWAnrAqWxqowWayPUMKG/krYHK2KXwZr&#13;");
		sbAux.append("YEecDnYP08OeZEPsT9YXAPo1TQPn0jRxPl2LNR2cZop0LKULe5yMtzH2lYcjy1sPvoayWDdnAkaX&#13;");
		sbAux.append("lyCd+aARecUoKS3HuHHjcPfu3c/KSuDwqSbNAH1DiPgxh4c+4D17diEyMhwdO7RD659aMsWRgY6K&#13;");
		sbAux.append("IrQVpKAj2Q6GEi1hKdkCfiptMMREFtk2KiiwkkOZlSzG2ElgqlMrzHT5HvPcWmChTxss9e+AFb06&#13;");
		sbAux.append("YXVvaawJlcbGCEVsHKAsALR1iKYA0J5EfexlgBxIMWBqo81URwPnMzRwYYQmLmZoMpi0cSalM44k&#13;");
		sbAux.append("d8GeVCusT3LC0owA2EiLYWJhKhbOmIjk5GSUVFQjL79QGOnfs2fP5ykm/IvSDNA3unxZDyHTSZct&#13;");
		sbAux.append("W3+Bl483lFVV8BODR1ZGCl20NWHRWRtG8lJQ/V4MFuIt4KvcHtF6kkgxkUaRlQzGOCpgnL006i1a&#13;");
		sbAux.append("YbpTGwbQT5jr3hYLvDsyBZLAykAZrAmWw7pQOWacVbC5P4MnWg3bh2hhV5wu9g3XY+pigMMpLFxl&#13;");
		sbAux.append("MP8zQhtnM7VxIVMLl0Zo4QJTorOpOjjBnnOhyB2zwrSwvaQfwk2lkMxS++UzJ2BUXg7KyiuF+UfU&#13;");
		sbAux.append("pk6d+g8A8S9IM0DfUH24waQPljxQ3/B+UFRXhaS8LBSU5JlZloeCeEeotm8LY0lx2Eq1h6dsW0Rq&#13;");
		sbAux.append("SWG4oTQyDTugzKwDJjp0wlS7jmjo+h2mObVnIawDC2ESLIRJMoDksLKXAtb0VmQAKWJLpDp+6c/g&#13;");
		sbAux.append("If/DQlhjnDYODmPqkqSHo6l6OD6ChbGszjiT3RkXs3QFgC6nMxVK0RJU6FKxKxb308TadB+UhFpg&#13;");
		sbAux.append("kIcpZtcVY2LdaIwqzEdhYSGKi1koKynB9evX//IJ9//fAySaldA3lH8rp0yZAlV1NcirKqOjtKQw&#13;");
		sbAux.append("RGGkrwsdOTkot2gBwzY/wVVGHBHaCojVlUGangTy9NuiwqgVGsxaYrLF95jE2hQnCUx2lcYMdznM&#13;");
		sbAux.append("9VHEIn9lFsJUsTZYDetClPFLBPM9/Rk8LIQ1xmjgSKwWjiewsDWcpfLJLAvLNMSxHEOcztbH+Sw9&#13;");
		sbAux.append("XGZKRABdZMb6TIouzuZYYsNQI6xNcUVVn25I7mmJxRPKUTUqB6Wj8jG6ulKAiADatm3bP6lQM0D/&#13;");
		sbAux.append("4oUX2AgcgomKbg8fPoSXlxdk5eUgw8KXrLIig0kFynJSkGn1PXTbtYCbojRC1GWRbKKBZG1JjNBo&#13;");
		sbAux.append("iyK9Nqju0gKVBmKoNxXDFLvWzANJYaqzHGZ6KGCetzKW+KkyBdLA2t4aWB+siu0RmtjJVGhflBoO&#13;");
		sbAux.append("MICOxmrgZDyDI5GZZaZClLofzzLB6RwjBoshLmVp40qGFq4ka+ICS/HPpJlj01BTbCsMRJq3DjIj&#13;");
		sbAux.append("PbB95SzER/dDXVU5xo9rEKbE1tTUYPbs2Z8n8osuGWoG6F+8iJb06Rs6b948SEkxj6OmAQkZeUgr&#13;");
		sbAux.append("KkJJWQ7qCuLQk20Na4WfEKDRCTFMeZJY+MpS74hCzfYo122Hyi6tMZopUI3VD6i1/hFTHCUxw1Ea&#13;");
		sbAux.append("s11kMd9DHkuYCq1gKrSaqdD6IGVsZeFne7g69rAw1hitjoODNXF0KFMhZqZPDNPH4VhDHE7sgsNU&#13;");
		sbAux.append("B8o2ELzQxVR1XIvXwKWhOtgeqoqdSc6YleSFwlgvlIwcjNrRBWggeOrq0FA/RghhZWVlqKiowLVr&#13;");
		sbAux.append("1/Do0aN/Ut9mgP5FH8QhIoASExMhLy8PRSUVdJJVhIwCA0hRBpry7dFFrgXsFX5Eb/W2iNWRQCqD&#13;");
		sbAux.append("J1etA4o12qNCpwMDqC2qzFqj0roVqm1+wmTmhzhA81gYI4CW+ylhVYAK1gQq4Ze+GtgaroYdEWrY&#13;");
		sbAux.append("E808UIwmDg7VxmFmpo8mMBMdZ4Ljw0xwNM2IhTM9nM7Qxq/Jarg8lKX1gzrjVIINNie6Y3xid5Rl&#13;");
		sbAux.append("haO6Jgt1DeWoqShFTUk56mvrkJeXh6KiIgGgvXv3Nleiv+loxadqLPcGN2/ehLGxsTA3WVZOAVJy&#13;");
		sbAux.append("TQApK0hDmwFkLNcSzootEareDnFa4khX64gc1fYoUmcGWodBZNAWlV3boNKyNaqt2wiGeoqjFKY5&#13;");
		sbAux.append("S2OWhyzLxJgK+TOIApSxMkgRG/qoYWM/FWxh2di2AarYNUgdexkcgplmEJ1KMMKpRGNmqA1wJI1B&#13;");
		sbAux.append("laLJgNLA6RgtHBtoiH1DbTEr3AJVQ71RPWoIKqpzUFg2EsX5uSjKzkF5aZnggUaPHi00qgnR30rV&#13;");
		sbAux.append("6WaAvuFFqDwzH7Rjxw5ISkoKAEnLMA+koAw5BXnmfzpBW7YNzJgCuSu1RB+1NohjYStNrd1ngEq1&#13;");
		sbAux.append("WBjTb4cKEwaSRRtUWbXBWDtxjGcQTXJhEHnIYI63Ahb4K2BxgBKWBMpjdZgy1vRRwobwv6fzOwYz&#13;");
		sbAux.append("U80g2scM9ekEQ5waxlL64Z1xIEkb+4epM7iYSg3Sx56BZljWzxxjQq1QkxqGhuos5BelISM3BSUF&#13;");
		sbAux.append("I1GWl4/K8gphlQet9hgzZowQyiiMiS4GaAboGw5pNDQ0COGLmoysPOSUVIVjZZZx6Uq3hpV8C3RX&#13;");
		sbAux.append("bolw9daIV2+DVNW2yFZphwK19gygjgygDig3bo9Ks/aotmgrFBMb7BlEzp0w2V0KM7zkMMdPDvN7&#13;");
		sbAux.append("KmBhoAKWBythZRhL6/soClVpKipuGcTUaIg6djOQTsSybIyFtENxGtjL2u5YZrpjO2PnEDNsHmqP&#13;");
		sbAux.append("5Ql+mJoYgqllaZg4rhSjyvOQXzwStcwDVZey2wVN6lNQUCDAQzDt2rVL+FtFV8Y2A/Q/vIgW0+iY&#13;");
		sbAux.append("/I+qqirkWLouKyvLzLMqFORloSLZHvpSrWDHAApQ/QkD1H9CvNpPSFVphSyV1shjSlSkxVRIj0Fk&#13;");
		sbAux.append("1BFVXcVRZd4BdTYdUWffEQ0snZ/kJo1pXrKY5SuPOT3kBYgW91bAkmB5rAhlatSXQdRfGRujmRoN&#13;");
		sbAux.append("otqQKo6ykHaU3d47SAk7BtFwhxY2xxphY7wd1gzvji2liZhbkISxpTkMlBKUVpWgrLocddUVwpBG&#13;");
		sbAux.append("TfVoLF26FAsWLBCSA8rEaJEiVdtpELkZoG+QxotOOI+MjIS4uDiUlJSEtVoqqupQkpOFaqc2MOzU&#13;");
		sbAux.append("As5yzECrtsBA9ZaIV/2BAdQCI1RaIkettZCJFekxL9RFHJUmEqg2E2eZWAfU23bAWEdxTHSWFMLY&#13;");
		sbAux.append("TG9ZQYUW+MtjUYC8EMqW95YXBlfX9qWxMRVsYmk9eaL9kfLYHyGDnaxt6c880yBdrI+zxNpkLyzL&#13;");
		sbAux.append("7IP1tXmYyuApY56nlAFTVV+LsqpKNNTWYNG82TjQuF9YN3b79m1cvnwZx44dw5o1a4SxseYQ9o0K&#13;");
		sbAux.append("iaIrS2mJsZiYGNTU1NCpU6dPAElDVeInGEn8AHe57xCq+j1i1L9HgooYUtS+RzoDKVu9FfK022LU&#13;");
		sbAux.append("J4CqGUCjTTui1rIj6q3bYyxToclOkpjOVGi2lzzm+TIvxJRoUQ+mQAEKWMHC2epgRawL++SHIll2&#13;");
		sbAux.append("xkDa1VcWu/t0wra+NG6mjHXMOK+Od8Cy9EDMz4vBwrpijK8qawpPYydg4tQZqB5di1kzp+P8mZN4&#13;");
		sbAux.append("+eKZAMulS5eESjSZ5wsXLuDcuXN/SUX6/3uA+PQN+jDpmJYed+jQQQhhKioqgpGW7NgOhoqSMBH/&#13;");
		sbAux.append("Dm5SYkizkkeWaQdESoghTp5BpCqGZGUxJMixYyUxFHRug2L9tijQ+h41Zu1Qxwz1WJsOTSk9A2gu&#13;");
		sbAux.append("GWlfRSEbm+UmjtnuHTHfqxMWdZfEsh7SWBUkKwx1bAiRw+benfBLbwn83EscmyLUsSnOArMGWGJi&#13;");
		sbAux.append("vB9Wjy/E1NpSYUVIRXU9qusnCNc1tfW4cf0qnjy+z3zOQ8HrUO2H4Llz544AEgF1/vx5IYx9WZX+&#13;");
		sbAux.append("lvOE/mMq0XShD9Pf3x8KCgpCIZGyL0rhpSQ7wkChE4zai8FLRgxF7hoY56uNUhtxDJQWQzyDJ133&#13;");
		sbAux.append("b8jSb4Fs3RbI0WkhGOlx1rJMhX5CTddWqDNvjQbLdphk2xFTHcQx3akTZrp0wkIWzhb5yGIpU6MV&#13;");
		sbAux.append("TI1WMTVaS+NkvVWwgSnSrgEq2N1fCVvCFBlA2lg10ByT+nVDfUJPLJ5QirG1FagfNx6VNROEVlMz&#13;");
		sbAux.append("Hhs3/IJ79+7gydMHePasaUorTeUgiGigmEoVV65cEdbt02N/5m4f/xF1IG6kKZz17dtXMNEUviSl&#13;");
		sbAux.append("pSDFYJKRloSeXAcYthaDv6IYJoSYYsNwd2wcZo9S2zZI0RPDEKZCSTpiyDZsgVy9H1FswMAx64BK&#13;");
		sbAux.append("45ao7toStd1aY4xVO0xgAE1mfmiaiyRTI0nMZZ5orqcUFnhJY6GPFBb7ymCpvwxTIuaLesowNZIS&#13;");
		sbAux.append("2gp2vDxQDQvCjDA+3AoTM/phxWxSmwpMnj0PlfWTmAcai6mT5+HBvcd49uIpnr98wlT1xedtYQgW&#13;");
		sbAux.append("gohCGu3ycfXqVeE2L2E0h7D/4YUknC93oaIbmWgKYRKSndBRSpaZaUl0lm0vABSm1QILBzngSHEQ&#13;");
		sbAux.append("ztWE4khJDyyI1keO9Y8YrCWGGBbChjFFytIQwyj9v7FwJoZCAzGUdPkOlQSSVRs02NGIPXkiCcxw&#13;");
		sbAux.append("l8EsD2nMYQDN6y6DhcxYL+4pj2U9lbC0lyJW9FHGihBFrAxUxvIgbcwJNsbECAdMyonC/BmjGTiV&#13;");
		sbAux.append("mDBrDmobpqKoqBbrV24RpuK+ePUcr96/xJt3bz9v/0IQkRJRKCNTTRDRNYW4L5c5fysV+o8BiI8L&#13;");
		sbAux.append("rV+/Hj/++GOTCklJokX7jgwkcehIt0WXNmIYxELTaqY+Jyp648rYvjhe5Y8z44JxpC4Ia9JtMTFQ&#13;");
		sbAux.append("FSU2P2GkvhhGqIsh34g147+h0OQ7jDL9HsVmP6DE/EeUmbMwZ/Ejqqxboca6JertWIhzbI3xLizM&#13;");
		sbAux.append("uXfAVA8JTPZmmZxDC1Tb/YiJju0xxVUe47y00NDXHlMLB2PujGpUjx+NqgkTUD92GsqKa3Hy4Bla&#13;");
		sbAux.append("AIvHL57gDfv39v3f9xASHSwmL8RDGYU17gVFAfoWEP1HhDBq/AOkKi2pj6KiIjrJyOK7tu0g3qkj&#13;");
		sbAux.append("NCRawagtC1PWctiS6Sssszlb0xOHKjxwZU4EHq1Nxp0lCTg/qT+OlvbE9kR7LA3TxyRfJeaXFDHO&#13;");
		sbAux.append("Rx71TGlqXCVQyWAot2+Lchb+iix/EFqp5fcos/oBFbY/oJIBU23XApUOLVHl2g6jXRhQbnKY7KzE&#13;");
		sbAux.append("HlfA6N6WmFWWgMULGzB2egPKGmhPoQmYUD8Ndy/fw8tHL/Hg6UNh/b2oAtHfSACJKhBlYwTSl5Vp&#13;");
		sbAux.append("0dDeDNAfVKC5CvEhDdrPp23btgJAP3boyMKYOFTa/yCEsExnFewpDMTxip44NdoX9xZF4fzMEBwY&#13;");
		sbAux.append("64fGMX44OyUcTxYlAssyWcvB66U5eLYsF8+WZOLxwnQ8mJeCu7OH4/bMBNyeEY9b04fi5rQhuDF5&#13;");
		sbAux.append("IK5NjMLlcZG42BCOi/VhOFvfByfr+uFEZRjOFIZhwwB7lNvIo6h7F8woicOyJeMxfdEUjJk2iWVi&#13;");
		sbAux.append("o7FiwWo8vv4Qr5+8xsPnT3H/xWO8evP68w5q9LdRAZEbafqynDp1SvBCBNaXu3d8izT/PyoL43OC&#13;");
		sbAux.append("aA4x1YIkpWTQWkKSZWSdoNTmbzBoJYaRHpo4VB6KX0czBartjmM1rthf44xjE31xZUEEbi6Mxo0Z&#13;");
		sbAux.append("kbg3LQrP5sXjzdqReLkuH6/WF+DVhkK83jgKbzYV4d2mYrzfUoQ36/Pwck0Wnq1Ix8PFibg7Zyiu&#13;");
		sbAux.append("T4/ClUn9cHFiP1yZzKCaMBCXq/pj9QBbpBq1Q7KzOqZVJmDTptmYt3I2Ziycg9L8Uuz+eQeeXr3P&#13;");
		sbAux.append("sgHaCOIlbjy8g5evX/2uiaadzqjASN7oTwGIf8BfrqUWnVf7Wzs/8Jn/JJ3cpP7WlrX/LsMZPITR&#13;");
		sbAux.append("MRXa9PT08FPrtlDV1EGL7/4GcQaUjQQzxt5aOFQciMt1gThX5cY61wcXJ3njwnR/XJzVC9fnhuL2&#13;");
		sbAux.append("gr54tKA/Hi8ahEcrk/FodQYerWMKtCEbTzaOFNqjn3PwcH02HqzJxIOVabi/fDjuLI7FrfkDcWNO&#13;");
		sbAux.append("OK7PCMONaWG4O40dN4Thck0/oYCY5iSH4G6dYKDdAoNiA7Fl9zqsW78Ke3/egpNb9+HdzUd484D5&#13;");
		sbAux.append("n3evcf3BbTx49FCAhhp5H1IfqkwTPAQOpfL79+8XgOJ9SP35rfpGTHRFo+j6KQJAdEkMn1PDofly&#13;");
		sbAux.append("/dFfuZz2f1JI5HUgHsZoCmhH8U5o3aZpowRdyZ9gwkx0HEvN9+T74Vdmnh/NCGUhx5MphQcuTe2O&#13;");
		sbAux.append("y7MCcG1ubwGg+wvDcX9xNIMjAfdWJ+PB+jQ82DACDzcyaDZk4f7Pmbi3fgTurE3HnVXJuLsiAbcW&#13;");
		sbAux.append("D8bNeZG4PjsM12YE4/q0QFwb2wPXxgTgfFUQZkcaIdFOAgM9NKAsK4b2ncTg5mMHP19PZMcNw41D&#13;");
		sbAux.append("p/Dq6h2cP3ICZ8//ioevnuLeg/u4f7+pETzkfaiQSPDwijQBRCGN9zP10TcDiD5g/sJ8OchvTUT/&#13;");
		sbAux.append("2hSQA8i3r/13AohP86S/kT5oX38/iFFGpqYAA8V2UGUqFGHYCmfGD8bhku44UuSEi2OZ+oz3xsXJ&#13;");
		sbAux.append("frg0vReuzeqDm3MjcG9+NO7Pj8G9pbG4t3IYHq5JwsN1aXi0nvmgdem4tyYVd1enCPDcXpGI28vi&#13;");
		sbAux.append("cIPCH/vZq8xTXZ3OsrwpZNQ9cH1cL9wZH4ml0V0R3VkMMQ6KsNBuDTmZ79AvIgBamipoxd7boonT&#13;");
		sbAux.append("ce3YGdy7fB1Xbl7FnacPcPN20zgYNe57qApN4NA+juSBCCB6nPebaFLxLwMkqhhf7uhA8NDg3Jkz&#13;");
		sbAux.append("Z4R0kFJDXpjib4aPNXH14aHw33FvY/7FoA+TLmvWrYWCpho6SLWBinRLdO4ghkDdH/FLUTA2ZTri&#13;");
		sbAux.append("UIkbzo7pjrNjfXB+Yg9cmBKEa9P74ubM/swoD8LduTG4u2gw7i4djHvL4/BwVRNID1azcLVyuADO&#13;");
		sbAux.append("3eVN8JD6XJ8/AFdn9cXl6SG4NKUXLk3qgdN1bjhT7YH7E8PRmOGJeC0xRBm0Q2A3Rfg6d0H0wGA4&#13;");
		sbAux.append("OtlAul17zBw3CWcPHcO1C5dw9dY1nLhwBpevXhH6hhpXHQLn5MmTOH78OA4fPizsTU3qJJq6f6ud&#13;");
		sbAux.append("O8S4pH1prAgUWqxGu4eOGDFC2DWUZJ82zKYOoDfwW+rEIfp3Aojvl8O/fZTm0jfw7ccPLMOZAA1D&#13;");
		sbAux.append("dWHnDfkWYnBRFMPsYS7Ymu+DEyyNP1Pni1/rfXBuHANoYiCuTunLvEt/3JnB4JnJ2rwo3F0YhXuL&#13;");
		sbAux.append("Y/Bg6RDcZ7AQTHeWxuH2ktimxiC7sSAK15n3ucLCIoF4YVJPnJvgi5Nj3bG3sBsulPngVLY3Skzb&#13;");
		sbAux.append("IcVIAn26yMBGuxP8fGzh4e0Eoy56qC4vw8nDR3Fw/wFcvHYJjUcOsFB2TlAbarRX9enTpwVwaHdX&#13;");
		sbAux.append("2sycprgSQOSPRPv6W63aEPsyU+G3iVzaxzglJUW4phlvVMWlfY2p0T7Hy5cvFzbHpjf+ecOCL0LZ&#13;");
		sbAux.append("v0MRkf9NBDz/AClToa/KK3zA+JnjENDDCcrtxIRiYnU/cxwZMwCNpd2FVP50rZegRBfG+bPsqTdu&#13;");
		sbAux.append("Tu6HO9MG4C7Lpm7PCscdIaT1Zyl/NANpIFOlaNxZOJB5pabrW/OjPoWuMFyaFsxA7MXg8cfp8T44&#13;");
		sbAux.append("NMYRx2qdcKncC4cSbFHeuQVKusphaFdlmEp9j/BgDzi5WkFOQRo9e/XAoQMHBTgIoEMnjuD4yROC&#13;");
		sbAux.append("2nDFOXr06GdwaM08LfWhPuKrcblAfKuRejFRc8k7ntSF1IeWz9KSEdq/mKCh2wQRTS2gidwEFz1G&#13;");
		sbAux.append("gM2fPx87d+4U5JOyAPIb/w4Aif5tdMxVk+YNv3r3Fk+oGId3uHHtNMYUJiO5ly2GOalgU34Qfh0b&#13;");
		sbAux.append("gRNVPiyld8c5FmquNHjh+nhf3J4UhHtT+rAMioUzFo5uzWLZ1Nx+AkT3FwwQrm+zdosZZn59fXZf&#13;");
		sbAux.append("Fv5CcWlyEM6P92dhkdTHA3tqrXB5pj9u1/phNzPR+dJiKOncEdk2neGrKwsPG0N4dXeCQVcD2Ls6&#13;");
		sbAux.append("YvMvW7Brz240Ht6PY6dZiDp6RDh1AjX60nN4qC8IHjrdAgHE/R8H51slO2I8K/lS2ohomiaZmZkp&#13;");
		sbAux.append("QEQtKytL2CFL2CWLhTW6TY+np6cLcBFM9Dya2E2hjmoQ/9sX0TDLz5bzubj44T2evWeGn/ZSff2c&#13;");
		sbAux.append("3cG+pa9vYnR/D0yIsMKxmr44Xsk8ULULLjKVuDrGGTfGe+L2RH/cZWHozrQQFs6CcYuFpTtz+uLe&#13;");
		sbAux.append("PJaZzY8Qrun27dkMMqY8dHxzZh9cm9ab+Z4AFg5ZltfQHSca3LGz1hLnpzOjXuyCHWF6GMUAKlBu&#13;");
		sbAux.append("iWQDeXgqt4ebhR5C+vpB31QPcupKmDRtKhoP7GMQ7WTwHMLBg02KRNcED8FCU1oJnl9++QU///yz&#13;");
		sbAux.append("cB+vRIuG8m+WxnMiRc//QCpCakOgpKWlIScnRwCKrjlMBAw9ThBRo2OCiZSJpo6Sb6L5urNmzRJO&#13;");
		sbAux.append("OkImT3R1KM+SvpTT39u2lpv1L8+tJdq+9sOhZz1+/ikbEfaDZn//09vYOaFYqAbvLQrB0fLuuFTr&#13;");
		sbAux.append("gZ1pGngyozuu1NvjZLkFrk5gnd7QFNoIlAcLInG63htXJwfiyaIo4fruHBayJvTEJQbcxfE9cH4c&#13;");
		sbAux.append("g7HBG7+O8cKZek8cH+OGI1Ncsb/aBmfyHLEnvAtK5MRQodMR8dqS8NOUhJW+MpzdLdHZWBdqumoY&#13;");
		sbAux.append("NHggNm3ZiBWrlgsA0edKiQ5FDIJm1apVwngfAbVs2TLhs+dJAy+/0PW3Wngoxr+hoqkdN5q0YI2q&#13;");
		sbAux.append("trR4nww0AURgDBs2TIAoPz//8+J+eh4Hi8NESjV8+HDEx8cLjeCbPHmy8EdT1sB9E/1eMnlk3L80&#13;");
		sbAux.append("5vSH/takqN8rKdAHRN+4r/mQmAjh1Yu3TTtsvmUf7rlfgXNHMWVIT6xIYr6k1BeHRtmgMVsPx0YZ&#13;");
		sbAux.append("4ep4FzxfGoqD5TYsFQ/E6TEsFNUxsKYG48aMMFyezFJ9pk43pgcxfxSCy+P9hHZpnC/OsWzuXH0T&#13;");
		sbAux.append("PL8yKE/UuWJfvR0O1jjgarkP9vQ3wUhJMWQptkC0Wkf468rBREMant52cPN0EvYscnN1xsIFc7Cv&#13;");
		sbAux.append("cRcWLpqLzZs3Yu7cuVi9erUwN5ogokZRYNOmTcL0Vm6guQekL+y3WrUhxqvMoufC4pVKOjkIKQo3&#13;");
		sbAux.append("zHTaIvI6pCoEytChQwUvRPDw8EbP5yGOg0SKRGpEPopAIgjpPlK2xYsXC98e+pbwcgAvJ4ielI2f&#13;");
		sbAux.append("g4tXuOkDEG2kbAQMXdNzvlqiaVP6ZyylpTyCtVcXr7KY/gRHZtSiopcpViba4hTzJ9eZihyrYErB&#13;");
		sbAux.append("ws65KT2wPM0Qp6aE4NzUPjg0ujuO1/kJFeaLzCCTKt2aGdwEDlMcahcYNOfqPXC2hqXtNa4sdXfB&#13;");
		sbAux.append("8Rpn7KmyweEaF1yt9MfmvkZIZyEsQf47hKi0gbuWJEy0ZGBn1xX+fl7QVFWCgaY6kuKH4sC+nVi8&#13;");
		sbAux.append("ZB62bt0ihCk6lRT5HuonOofHunXrUFdXJ3gjLgyiO7V90zRe9APnxUS6TRRTRxMktH0ISSM/9xWd&#13;");
		sbAux.append("2mjGjBkCONwjiSoQQUSAEGTkj/h9pF50X1xc3D+oEz1OGx5Q0Yu+MXwZDh0TFL8FxJdjO3yXrv9O&#13;");
		sbAux.append("fH/5/IWwX/fbF++bIKLP9dZdpkLHUNXPHdNjbHFsTB8cYdnYsVqmNhN748CEEByaMQjH57COHBuC&#13;");
		sbAux.append("05P64lBtD2aKe+H02ABmkP0FFTpZ5SKAc77OA+dq3ZkZdxX81KlKJ5yscMTxCgc0VtjjWDUDq9QP&#13;");
		sbAux.append("C3pqIFFRDDFqLRGg1RH2mp3gbG2Ibl314GZnDR8HBxipqcFURwtTxtdh757t7DObJIQq+mJTn9CK&#13;");
		sbAux.append("DFqdQUBR1OBb2dBnIgrQfyfU/yFAotVofkY8upA0koJQo0yLzipDb5AoJ3kkkMio0RunUx5RyCNI&#13;");
		sbAux.append("uLrQz5E6jRo1Svh5epzCGClQTEwMBgwYIPio1NRU4Zruo1UTpFZUf6KlKvReCHB+/ixe0/my0v1f&#13;");
		sbAux.append("jdX9QQATpoXSBJvnz5i808sxjh5fusG80CMcXzoVoyOdsTY/GGdnDsO+MZEoC+2MPuat0M9WDj2N&#13;");
		sbAux.append("W7LH9XBgTDBOTOyDHSWeOFjtw1TKH6fqfBggToLinBPAccaZSkecKXfAyTI7nCi1xdESW+zJt2GZ&#13;");
		sbAux.append("ni/OlgVigqcSohXEEKbeEv4mcnC30ERkmB+Ce3jCw8ICUf49YK+rB/X27RDawwczp43HooXzBRWn&#13;");
		sbAux.append("Rkt6yB7MmTNH+DJTEiMKjGgZ41tlYp/rQF+GL6FSy+InQUDrrsm70BsjiMaOHSsAQ41oJ8mkuEt1&#13;");
		sbAux.append("B1Io+kMIGAKDQhopDSkPHZOPotfj5QG6j0IbgUNAkTqRItHpkOikbIMHDxa81sKFCwWzSD6JV7tF&#13;");
		sbAux.append("B0i5coqeVvKPo9c7vH73mDHDwuL7tzTIDYFJslsPmT97/QTz8uOQ37sb1pUNRH6QMczFxaD1kxgU&#13;");
		sbAux.append("WbNU+Ql9TX7AtFgTHJ7QDztKu2NHsQeO1nRHY7ED8zleOF3FwhVTHALndBkz4AyaE8U2zE9Z4ego&#13;");
		sbAux.append("W2xL64rTlQG4UNUXZY4yCGIm2lezBYKcdBHWyxGJcZHIHD4UwU4uCLV1hEdnfdhpaaGLhhI8XO1Q&#13;");
		sbAux.append("UV6KadOmCf1A/UNRgz5PUp8vYflyUPybACQaurhR5QARzaQkFMIIGgpjdE3nuKI3SwpBYYdAmjhx&#13;");
		sbAux.append("onBMfwjFX4KJ4i99M+gxyujotahxA04wkTqRMtE1wUoA9e/fXzgJG518jRrdppO40ZIcCof0IRGo&#13;");
		sbAux.append("VGogoESViP6G3zPdXwL0Ds/w+MM9vGJHj96+xyPm4d/T9r+PXuHDvTvMxd/GuBTWiYF2MGPwaLcW&#13;");
		sbAux.append("g7WOGmxMLRDXrzfSexogwe5HrCv0xsGGMOws9WbpeQD2jXLA2TpvFqqccYrBc6rUDqdKrHGiyJKB&#13;");
		sbAux.append("0w1HCixYs8aeEVY4VdaTHQcgxYSFLlUx9DKTQmRvGwwa2BPxseEYGBaIQGtmpNV14aOjD19jUxio&#13;");
		sbAux.append("yEFTTR6aGmrC+cdojhPN93Z0dBS+iFTc5StTubfkAvEta0Fi/MW4QRUFiOoH1GEUlshQE+nTp08X&#13;");
		sbAux.append("vA/BQstouRJRo/voOZQ6UvijuMwNHsFImQLBROAQSPTaFO6okVrR7yG4KMyRJ6Ln8FBHp42kE7FR&#13;");
		sbAux.append("I2UixSKVogyQ1ImmLXDlob/ja0xiE0BP8eLDY+GEK3efNp32SViTxz6CDTO3ozquCJHWbvDXUxe2&#13;");
		sbAux.append("+DWRl4BMm3Zwsu2BipEVWDcmFz00xDB5iDWOTYnBvtE9cHFaGA4xaE5UMXDK7QV4TpbY4ThTH1Kd&#13;");
		sbAux.append("A6Ossb/QBntGOWJ3RU9sqwzDohQfBGn9DT302yPKxwQJ/f0wNKonhoYHwEpdEZ7qDBQpplA6neGj&#13;");
		sbAux.append("rgoXbVX4WZvA2VgbnRUkYKAuBzV5SQT19BPqQsIOxh8/ZZm0E/8HfN5Q/e1bFsqY7r7/+O7bhLAv&#13;");
		sbAux.append("x6/oNqXYlBmRHyFjTI06ikIZKRCBQsDQ7d9rlAnQ85YsWSJURckzkXciH0WAkRL9VhZHANF9vNpN&#13;");
		sbAux.append("74PCmShMdDZAOlVkYGCgcLpICpWLFi0SRqXp7+F1LdGxML69XRNgtL0vy+AePRNOeUEG+tp5oL5o&#13;");
		sbAux.append("A3p1K4aHcib8lVIQqBqOKEtnYbTeXKkTjFQ1YGsRhPgBeahNTkVAZxlMiPPBhtJgrMm1w7EGN2zN&#13;");
		sbAux.append("1sHxMlMcLzbDsSILHC6wRSNTpX1F7thd5s2yL19srg9GRZY3ggOYSbaWh6eVOnys9RDu7YDkvoFI&#13;");
		sbAux.append("C+0Fd00FeCnLwJ+BG6IkhQEaihiorYJwNUX0VhZHH50O8FZi4VRKDEXD+uPCsQMCKA8eM+/4vmkH&#13;");
		sbAux.append("448fmrxd09lk3guniXr3qX38F09aJyYaH0UBopSYxouoAzlA1OEEA/kgCmfU/gggCmmkSPy5PF4T&#13;");
		sbAux.append("QGvXrhXK7pTd0eMUt3n9iJtvCm8U/ug2KRKBRKpEYY3OR0qnlqS1XqRKdIpJC2Y2qRFspJaU2tJk&#13;");
		sbAux.append("KtHMjZcE6JRO7540yc6z68CU0Tvh0GUADKX7wl+3DuH6s9GjUzkG6rAM0q03DL8Xg2ZLMejJiKOb&#13;");
		sbAux.append("njMC3KLg180VXno6mFsYj8ZZmdhaF4z94/ywKd8EjWVW2JlnwfyQGw5X98bB2gj8UhaGhZndUR9j&#13;");
		sbAux.append("icwIY9jYslTdVh4ePqaIDvfDkLBeiPR0RVBXU3irqaC7nBSClaXQX00KUaodESnbGpGSLREl0xox&#13;");
		sbAux.append("Cm0Q2kkMQ43EEWoghT72XXDn/CkBoJfs+/KMRfZ3H0UA+vDxM0D8358CEE/5KIWmjuPDGQQCAUAA&#13;");
		sbAux.append("EUhfAxB5JepI+hnaGYM8FN1PRpsUjV6HVGPLli1CyKRQR/dTyCSYyHzzMEqhjRctuWJROCSgSIXI&#13;");
		sbAux.append("J0VERAjnHaUt7AguMzMzASx6LoVV8gYiSZiQuu9dewBhnhnoLNUDnl3jEGxTBG+VCvhL1SBMqhyD&#13;");
		sbAux.append("1IajITQKKc46cGQmV+V7GrlvBTsDW7iYMG+kpgE/S31EexshO8IcM3O9sbTIlxnvIKwrCcXS3DBM&#13;");
		sbAux.append("iuuBghBHxLgYwt9IHjbMgHdhr+XpoApfT3309rFApJ8jojxcENClC7yUlBGgooxe8p3QT0kcA9XF&#13;");
		sbAux.append("MYj9TLTcdwweMQyS/w5DVVpjsKY4nFuJwV9DFqvH1dIG2AJAT56/EXIB4cR2H5uUR0gzP74VUaD3&#13;");
		sbAux.append("+PgtQthvDR3wJSJURKSOpM4jYMj/EAzkZQimPwKIABFtdB833RxEDiOVCHg9g7wTQUUw0WP0Pghi&#13;");
		sbAux.append("7psIajL3pEqkVJTZUagjgCis9erVC97e3kKYI5icnJzg4OAgQEVqRWWCHVu2Y9royehuGYgu8v7w&#13;");
		sbAux.append("MBkOD6NYuGsPR6BGDYIYQANlKxGrNARLh8RhXXYg8gOUEWL6IwzbiUGn/U9QE5eAtrw8dOTaw0Dx&#13;");
		sbAux.append("RxizNNxYVgxOzBf1NO4AO4XvYCPTEhZSbdFNRhL26irwNjFkWZa1EKoG+tlikK8VC5m68NBSZP5G&#13;");
		sbAux.append("Ge7SkvCVlUS8qQEGqEsjUqk1Ihg00fJ/Q5xqKyRqtsUwtTYYrNIR0aoqCFRQw7ihw5pkhzHy9tVH&#13;");
		sbAux.append("PHr28hNA7z+d2ZBlrB+bTrz5URg+prMpfvy2AH05DYIgos7mIYW8D6kQdTwdk7L8EUD0rScwCAKC&#13;");
		sbAux.append("hp+lmB6j+2hTJFIlDqSoAacsjwYGyYRTZkdKRT9HnU/Kw0sBHHACisoHpF6kUJTJDRo0SDizso2N&#13;");
		sbAux.append("jZCt0NmVPT09YW9vDztLe3h0c4e/dSgzqmEwV4lg2U46Agwz4dIum4WHKsRIFiNbOx6/JA7Bnjxv&#13;");
		sbAux.append("rM00w87RPigO0YGFpJiwi72xihT05FvDvosM3M3lYKbyHSxUWsJBWwLOOsrwNOiMnmaWCLJ2QpCt&#13;");
		sbAux.append("M/ws7BisZnDX64zuWsroraMCf0VZ9GStn4YKesl0gF/77zBApT3itNsx5fke/RlAA6TFECPfArHK&#13;");
		sbAux.append("7TFEQRwDZBTg0UIea0dOBqheeO2NcFLWR/dfNY3MCJAQMDSk8/RTeyEARCeW+fDxG1Wif2tMiXsF&#13;");
		sbAux.append("6kQytgQQGWLRWtDXAERAkGoRHASMqBrRbX4/PZeDySEjqCjs0e/hQylkwgkmUia6nyDiRUsCiENF&#13;");
		sbAux.append("KkXhjrI7up8Ao6yN/JKHh4egRPZWdrAzsoWDgScC7JPhb5UOc+XecFYbjEj9BvSXr8GgjjnI1YzG&#13;");
		sbAux.append("obTBOD7KGUfKzXF1ti8aa71ZWOqGjCAj9LZSgGFHMeizNN9KRQxmLDR1Y0pkq9oG9qpScFBVgCPz&#13;");
		sbAux.append("M04se3JmaberljrcGDQET6iaKvrIKSBYiimNohyGMBUaoNQeYczbkOoMVvsbYtVZuFIl39MSfdu3&#13;");
		sbAux.append("RmhbCUSKa7JwZokozV5YnrUMH46xTiOrdx+CpxNO58kyA+ZmWXvMbtN42GMBICGMcW/0ZwHEL5R6&#13;");
		sbAux.append("UwdRZ9A3nwwwdS4pB4HxRwB9qS7UqONJ2aieQ42HMnoOz+7oPnqMjjlI9DvpmBSKRp0pq6NqK4U7&#13;");
		sbAux.append("+l10nnVSHsrGSDXJlBNQooO9HCbK5tydXdDdyQNu3bxgptUDNnrh6G4zGO66g+AumYoolWrEdByB&#13;");
		sbAux.append("LKVQnMuLx7XR7rhS1xXHyjXx63h73F7YD7/OHIAD4yOwocQPY6L0EWvXBr10xOCjKoYA7e/hysKZ&#13;");
		sbAux.append("vQTLklo3NRemWiE6LRFvJ4dcd0MU2VsjUU0bAzpJo3+nDoJBjpb7AfEarRCv3QJRiix0Kf+AQcod&#13;");
		sbAux.append("0V9OERGSzGvJOCFRtQ9S9JNQ4jUWsRYFKOk7Aw/2M4051wTQ+9c0TefZJ4DuM4DufYLomQAQPnxj&#13;");
		sbAux.append("gP6rC6XcvJhIPoRCC3UuzVD8GoAIAlFFIXB+y2TTYwQbgULPp8fo/i8VidebuHrR+6H3SIVLCnUE&#13;");
		sbAux.append("PL0m7VhKSsQnvhFUBBcvG9A1VXFpYDI8OAIBXoPh4zIUzt3C4KTPsjCNbPRXLcVgyTQU6UbgavEw&#13;");
		sbAux.append("/FpgjjvjTHCxTh2XxnXFmVpznKmxwXmWtj9a2FdYafHrxCBcnj0Q+2t6s5TeE7NiLTE7zgbz4m2w&#13;");
		sbAux.append("MMEaC+O7YUGsMRYM0cf86K7IN1XDIAmWXbVjBlmyHfp1+J6FqhaIZ+Z4sFo7RCq0Rah0RwSJK6Kf&#13;");
		sbAux.append("rDkStPoi12wkSm0moNRxLlItpyHBejJibMYgM2gmzm5h7ubupzE9gkQIWQ9Zu/9PAOH9XwAQzbGl&#13;");
		sbAux.append("LIe+zfycDDwt/xKG32qihcbfMtYEhSgY3GhzBeK+h9eeqHEDTvcTyFypSJko1JEiUaijDTW5opEi&#13;");
		sbAux.append("UQmAD+jScElJaSH7m5KRkzsC2VlVLIsbDlMjV5jr+KCnwQj0VmIQSSWiousgnMmNxuUya1yr0cWV&#13;");
		sbAux.append("WlVcn2iCGwyiBxPN8GiCGR5OssHDKU64P90b92b1xJ3ZvXFnbgiweRgeLOyDGzN64MnCELxZ0RdP&#13;");
		sbAux.append("5/fErclOuNbgg1Rt5m1Y6Av5SYwpUEsMZT5ogKwMQsSlENheDiGSnRGt4YIRFjGo8KxAvf9cVHut&#13;");
		sbAux.append("wii7NcjothqpNqsRYz4fcXbz0N9yLOK8x2Hf8rtCIfT+lad48YTC1nO8fncXr97eESqkH5g8Ue2L&#13;");
		sbAux.append("Fxf/VIAo7aWRc8pyCCDqjG8JEHUwPcbDGL22qCoRIDz954abQ0GV8C9NORl2KhFQqCV1It9Ek6vo&#13;");
		sbAux.append("mubL0M9R1kZZXO5IZr7zhiG7IAUpWfno038YDEwsYGbgCm/DYQhUT2c+JB6lZgNxcmQTQDdG6+Jq&#13;");
		sbAux.append("jSpujTfC7XFd8GSCPp5P1MOzSSZ4NsUST6c54MlMNzye44lHcz3xdIE3ni+mY0fcnNKNgdMN96Z2&#13;");
		sbAux.append("w6UafewboY5wBk+smhhGdJFDmr4W8zv6GKJmhUQ9f6QzcEc556HQpQrF7pNR4r4QRW7rkO+4BTk2&#13;");
		sbAux.append("25ButR2pdtsxzIZFCZdNSHNfjSiLsRjqVof1U44JEFEqdvfOLQGctx8fN8U3uvPtR7x88urPB4hm&#13;");
		sbAux.append("EVIIo6yHpJ86gDrpj8D5WoAIAK4c1OkEDM255iGSFIimK/AxN7pNwFA4JSPNX5urEjfi3F/x2QM0&#13;");
		sbAux.append("4EuFSwp3dE2lgvKKImSOjEV2UQoSM0dgQPxwaLMU28zUES4G0czLpKCv3BAUdYvCibxoXCqzZQqk&#13;");
		sbAux.append("xwBSZwCZ4PZYYzyZxCCaZICnU7oyeKzwbIY9ns52xpO5Tni8wAm3ppvh8UIbPFlgjTszTHF7alfc&#13;");
		sbAux.append("m2HJ1KcrjhYZYWKgNJbHO2Nj+gDM7R+NSo9BKHfLxdieMzAlbCMaem1Dtf9O5nV2It91B3KcdyDb&#13;");
		sbAux.append("ZTdGuu9HnudhDDbdhHyfE8hxa8Rwm3XMD81lWV0JUvzGYc/iS3j/gLHy/NOA+cc3ePvuE1Uf6LSv&#13;");
		sbAux.append("z/58gGg4gyrBZDypUWdRp3yN+nwNQAQEPcYNL8FKikfDFZRyU1GQRpdJlQgCmsxGSkMmmiATDYG/&#13;");
		sbAux.append("9Tu4pyKY6HmkSuSTKLwVFOYgIzseuaPSkJCRjliWbWoZ68LMzBa2nYMRaJCEMHmmAhb9cSw/GhfK&#13;");
		sbAux.append("7T8DdIeFr9tjTVnoMsGjKSZ4wlTl6XRrPJ3pgGdzHPF8HrteYMvCliXuzTbGg7mmeLnEHs8XuOLO&#13;");
		sbAux.append("ZEfcmuDOQArDhUlDcGJcJnaMGokFQ0difHAVGgLnY2zANtT5H2GKcxQFrscx0vUYct1Ycz8sgDPS&#13;");
		sbAux.append("+xDyfI4irtt2FPc4j3TbvYjpvBxZ9psxwnEFoszqMMi5HBvnnBJE5+7tF8KsXWEw9d0bvKMZicI4&#13;");
		sbAux.append("x58MEI3o0ok8eK2FOoEXE78FQDwdp04mMAgomkZCgND4GRlfGmmmIiCl5KRS9Br0nuj4y9cVHfDl&#13;");
		sbAux.append("Jp+Ap2OCiUoRdE3TT8oritnfNRwj8zKRlJ6FpMwcdO6qBxPTbrDWDUBvo0SEKQzAKMuIzwDdGG2A&#13;");
		sbAux.append("66M1cXdsV9xk7T4LSw+mWuDhNGs8nm4nAPScAfRyvi1eMdV5t9wGz+ab48kcC3btgkczuuP6WD9c&#13;");
		sbAux.append("HxPKwlkKTjcUYe2IPEwJz8LY4EqMC1mA+sBfUOHdiELXoxjldhaFHhdQ6HUOed6nkOt5FFnujch0&#13;");
		sbAux.append("2YEMx10o6n4aWY5HkGK2Gxlmu5BpvgNJJqswxHgWoizHICNsOi4d/ihkZTSm+l6YsvIab55Txfrd&#13;");
		sbAux.append("p3L8nwgQFROpYyiTIYUgcKgz6Bv9LQAiEOgx6njyNHyyPvktUhuChEIOjW3RzqpUZabfTz6H3oNo&#13;");
		sbAux.append("fenLKrdo9sY9FD2XAKKZjxPGjUdWehpyR9DofzHSRhTAxNIYhoZGsNLxR4hxAgOoPwMonAEUJYQw&#13;");
		sbAux.append("AujGaHUBoNusPZhqhQcMnkfTbIXw9ZIB9HqOHd7Ps8b7+d3wYaEZsNwOWOyM5zPc8GRKEF5MH4qH&#13;");
		sbAux.append("41NxriIPy2JHoSG4FOXdq1AfNB8T++xAXdAhlPsdQ5nfWVT2uorynpdR5P8rCnyPC8qT67EP2W57&#13;");
		sbAux.append("WCjbi1E+p5BqsRf5tsdR5ngGGQY7kaK3EemWGxFrNR/htnUYHjFJyMpo2K9pTPmDkOe/f/XkzweI&#13;");
		sbAux.append("6kPUiTwNpk6hDuRjWt/CA1HlmNJqApR8FqXeBBDd5oaXQhiFLCsrK2GIgisWqQ031dyME1i8JMDD&#13;");
		sbAux.append("LYFKP0OqRmDSMElNdS1yU7KRk5SH/OwapKcWwtbeCrranWGr44dQ4ziEKUagyLIvTuT3F0z0zWo9&#13;");
		sbAux.append("1lTxoMEEdxpM8XiqpdCesfD1egZTnJnWwCwrYA4Dh4UtzDIAGEiY54iP03zxYWo03k7OwM3iNOyO&#13;");
		sbAux.append("z0eDVy3KPRejgmVW1X5bUdOjEZU9DqDYbz+DZj8qex9BadABlPTcx27vQ2H3/Sj0Powiz+OsnUCa&#13;");
		sbAux.append("zR5k2R1CidMZFFudxEjDgyi2OIqR1vsQqTsLkdZT4GyQihULzoDGlD9Pk/r4Gi+f3vk0WvYnAkQX&#13;");
		sbAux.append("mhRGqS8HiDqNOudbAESw0Ovyaa2kdBQuCSDuvcgT0f0EEcHi5uYmhDS6/VtjbV/WoThg9Hwyz/Q7&#13;");
		sbAux.append("qQg5d/Yc5KZmIDNxBIry65CaUgBHZwdoquvAXo8UKA6hiv0/hzBSoFvV2rhdpY7HdTZ4UGvPFMWG&#13;");
		sbAux.append("mWcLvJxqjTcMoo8zRACaYwzM7oIXE5jJHmeLDzMi8XJSLk6zv2lVaComuBVijM9qZpSPYgxrFb4H&#13;");
		sbAux.append("UOF3EPWhJ1DXhylK4F7WdqMsaKdwXRKwF0W+jcj3ZAbahSmR00FkOuzHSKcjGGlzBDld2bXJcYzQ&#13;");
		sbAux.append("P4jhejuR2HULBlsuRaTDRPjbZOLK6fdN/lmo/9C04Kd/vgLRmBjNf+adSuk89xkUGv4IIHouKQcp&#13;");
		sbAux.append("AcFH19yjiC5a/K8aX4dG16SClOZTBmZkZCRM56DXI5j5cAkNvVBFmsKhqC8ikCjskULRYwTS2nUr&#13;");
		sbAux.append("GbzDUFo+EonJGcjKK0ZASCCUFNVhrOYMR6UghKrFIV4nFIvCvXGm2BZnCqSYeqjizeg+eFwSgleT&#13;");
		sbAux.append("vfCIpeaPxpnjxXhLYIYDUxsnpjoMpHkMIuZ/PizywPO5fXG2Jh4/D8/B1OBa1HdfjBpflmH1bERV&#13;");
		sbAux.append("z4Oo6nUI1YEHUdP7MEaHHEFNKLVDqOrNVCioERWB+1HWkymR/wHmew6y0HVIUKIcl/3Idj6AbPvD&#13;");
		sbAux.append("GGF9nKX+p5BidBophicxvMsBxOiuwVDzBYixq0dqSB1esoz+A4PoxdsPePXh3bcZTP0jgMhwkkpw&#13;");
		sbAux.append("gPio/NcAJKpCXCGoIynEcDh+rwn1GhbW6Pfy2YsEA4U8S0tLQbX4++EZomh45QDxgWB6z2SkqTRA&#13;");
		sbAux.append("GzelZ8WjoDRbMNGZ+WUIDAuBohIDSMMJTup9EKKRikT9oVgyoB/zLN1xpVIF96o18bIyBDdzeuB+&#13;");
		sbAux.append("rSMeTGAZ2GQLPJ1gjqdjTZjiMO8zyxlY1BMf5vXF3YkDcaQoAavisjEpuAYVPgtQ5LWDgXAU5QHU&#13;");
		sbAux.append("DqKCAVQZxEDqfYgBdFhoTQAdFO6nx5sAOsRU6ACDh0LZQcELZbk0sszrMAtnx5FsfgpJxmeQZHgK&#13;");
		sbAux.append("yQbHGPxbEGuwDDHG4zHUqQo/zzoGyuRffWwaVn3/ZwNEaR/t7kCdzTuSOuZrAaJvPAHDB0XpNoUS&#13;");
		sbAux.append("GjXng5+/1wgUvjqWfobCGb0Pek1K82kOMFc4/n7ovfFsjQNEv5ePwZGZpmyPKtZpaTTToBjpGZXI&#13;");
		sbAux.append("ZWEsNLz/Z4A8dKMwyKQK0arxqPMMxeG8IFyu1WNZmDwe1VrhSa0rLpUY4uaYLsxAd8WruV3xYoYJ&#13;");
		sbAux.append("Hk5kqXuDN+7UxeBK6SjsSazBnF71qHEej1LH2Sh1X4kSv58ZDFtQFtAoAESNA1Qd/HeI6PZ/BVCB&#13;");
		sbAux.append("1wHkuDIz7dyIDIdDSLU+xgA6wbKw40jqchRJBo2I1dqIGJ1liNSZiEjz0YgPqsWNix+EkY4n71/+&#13;");
		sbAux.append("+QDRNA/apIh3NgHEQ9HX1II4MKQa9Hw+8i5qlP+o0fMIHApN9HPR0dHCMTVbW1shQyNo+GvztVEc&#13;");
		sbAux.append("KF7t5gpI74WKl7Tje0ZaPjLSS5jC1mNkQQMiBw6GsooGumjawctgEBJtpyOgQwKStIOwJTEKV8Y4&#13;");
		sbAux.append("MyVSxNVKLTybbIOHkxxwa5wlrtYZCcXB2+MdcHtcEK7VJOJcUTWWhYzGJLfpKO26EPkmq1FktxmV&#13;");
		sbAux.append("PjtQFbAJ5b02orQn8zk9WdYVcLhJhQIPf4KoCSCCh+4jwEp7MIPsR+HrgKA+HKAsp30YYXeEAXQC&#13;");
		sbAux.append("yd2OIcn0MIYb7WEQ7UCM2hrEaK5AuMZsRJnPgFvnFKxecLxp4cnrR99mRuIfZWG06xUZWgpjlBWR&#13;");
		sbAux.append("mlA4+NpiIn3z+dwfUg7Kur4mfPGpGTStlUIZGW0+qYwXNWlqK01hpfdEr81DGf0+XqsSHeHn2Ro9&#13;");
		sbAux.append("b8vm7cjPLUNqUiHycsdiZF4dBicMhbqmCvTVu8JFPwJRplMYQIUYKD8Mc4PTcKKkH87XWONcjT4u&#13;");
		sbAux.append("1RrgWr0tro3xwM1xAUKoujkuA6dKCrE5rhILek9EpfV0FHVdhZHG2zCy6z4UOzAgvJjS9NjHIGIG&#13;");
		sbAux.append("2b9RAKMJon8MZQQRwUP3EWClPQ6j2PeQ4H8IngIvZqSddzOA9iPDlgFkyZTH/AiGm+7DcOMtzANt&#13;");
		sbAux.append("RJzOesTpbkSE2mrEmK2Bo3IOkqIm4v6jl3j+/i8AiG82RZ1IoYQAIg9BnfU1AHGzzQt5BB7BSCDQ&#13;");
		sbAux.append("PKM/AoiGUHgJgQCixtfkk5mmsGZoaCjcT0rH/RXPEnmNiRpfmsSVauvW7aiurEXS8BHIz6thIawc&#13;");
		sbAux.append("SZnx0NFXgpaqNqw1A9BLowZ95aciUqoSKRqJmNU7HCcqBuDalGCcrfNksPjjyuihuDW2EBer6rEr&#13;");
		sbAux.append("hf2NvapRYlGNVJ0xLLX+GaOsd6HAupGl1ix7smlEoeN+FsZYSOrOmt9hISwRHFyJCBpBiYKOfALo&#13;");
		sbAux.append("CHvsCAt7LH3vflgwzwRPvudBAaBsBxbCbI8xgFj4Mj+E4V1ZBmaylqnQKqSZbMVwg12IUt2F/vrb&#13;");
		sbAux.append("4KXeAFfjVPa3H8BbPBdWpvzpANEKBhqfopFsAoifYvpr1IeA4aPlvH7EfQ3ff+j3GgFCIYpPDCOQ&#13;");
		sbAux.append("uCfiasinr/LBVAqxlGnxMEvw0O8mBeQZJD1n+44t7D3WsfcRi/yCUcjOz0FmYQIMTOShpiwDY0UH&#13;");
		sbAux.append("eKkUIUJ1CfqJz0Nwy1HI0B2GeX1SsSUjEdsyhuBg1kjsTCjD8tBKjHVgyqpXhxTNKUjTW4pssw0Y&#13;");
		sbAux.append("abuZpdmbWNrNjp03IseeKZHtPoxyOIVi17Mo9TkhgEGNICFTTcBUBh4VGh1X9Dr2zwB5HkG+B8vC&#13;");
		sbAux.append("nHY1AWRzQgAo2Ww/U6BfkGi6jKnQUqSbbmVGejditX9FX7X9COy8FHaa6cjLGo1nLx/8+Wk8D2Pk&#13;");
		sbAux.append("NzhANLGMvu1fAxCBxseiqJ5E83ToNQgAvp/Q7zVaBs3n8PDhFAKPpmYQUAQz3aevr/9ZYfhyIvI5&#13;");
		sbAux.append("HCCCie7jJQR67k4G0Nw59UhOGYC8AgbPyBHILU1EFwtFKCt2gK6cMdxVMhGmuhBRCpsRI7MWiaoT&#13;");
		sbAux.append("kKSZiTTDASiyGoxio2TkaeciQ6UC6WpTkam7HtnGe5HBwkiy6XYMN/8ZKXZrkOm6FiPdNzKQWNh0&#13;");
		sbAux.append("Yj7G4QxGORNADCRfguMoSv0ZQD2PCcBUBh4XGh3TfWU9jgrPocyt0OsoA+gYCt0PI9dxF4OyEZnW&#13;");
		sbAux.append("p0QA2oLErosx3IQ1vfWIUd6BFIPb6Kd0EuFdtsLbsBw93KJw4/rlPx8gvuqTpkLwvX+o06hzvnZS&#13;");
		sbAux.append("PV/RSj9DMFAII3hoojtlUrSigrwMn6/DV19wk00DrXQf38iB4CNPRI/TfaRSpEI0eZ6ApayMxtj4&#13;");
		sbAux.append("MAYf2uCrZ/lQyPx5M7F40TgUjaICZhKS0lh4LMuEg4cpFOXbQENKEz26ZKBv55no1WElIiW2IlZh&#13;");
		sbAux.append("EwZJzUSsch0SlGuRJj8D6XJLkKa4EqmqPyNJi3379bYhoct2xJlsQ6L1Dgy33Y5k+61Id9yObMed&#13;");
		sbAux.append("yHPehwLnQyh0oYryUUFRRvkcEdSFPA41rkpNt498Vh5SnTz3IxjpekQoJmbb70KWLQOWwZPW7eTf&#13;");
		sbAux.append("ATJdIgCUavgLBiruQmjH4+grdwZhOtsRYDIRnlYDsGPLTnx49/dtnXlfi65W5vsw/VczVr+qDkSZ&#13;");
		sbAux.append("GI1HETzUwaQilOV8zbIeDhApEKkWqQ8HyNXVVTgBHHU8peN2dnZwd3cXVk2Q7yGF4WvCuPoQOPSz&#13;");
		sbAux.append("BA75MgKKnkPLemmus2jpgI/Sc4g4QKRCwpyhuTOxYtks5GalYEQGrTvLRU5BLjx9HKGk0B5qUirw&#13;");
		sbAux.append("7TIEfQzGopfEQvST2IkY2f3oL74Fg+TWYKjcagyT3ookqV1Ikme+Q3UbhmmxkMG8RqzRdgwx3YkE&#13;");
		sbAux.append("ywMYZnUQSdaHkWZ7CJnMRI90akSBy14UurJQ5nlIUBRqHCIqFBI0HBxSHXqMwhaHJ9flKAuJh5Bj&#13;");
		sbAux.append("x9J4G5aFMYBSKYXv2sj8zxYMM1mORKMVSDXagSEqjYiWPY/+iufQW3Ur/FhKb6MdjvryKXj57O3n&#13;");
		sbAux.append("jSj43uB8EeY3AYhenMik6Q8UTngxkQD6mpWpfKCTV4IpFHJjTKsjCCKCho7Jx/j4+AiNjgksWnFK&#13;");
		sbAux.append("S3VI/ej3Eiz08wQXn+xPENEaelp1QeGVz1fiGZcoRDwro+fQUMbPq9cjLZGpWXYtRqRXIS1jFIJ6&#13;");
		sbAux.append("h0BFUQYqkorw0OuNUMMC9JKZhD6S6xEl24gI8T0YILsL0dLbMVSaQcKO4xV/QZzaJsTqsKa/GUO7&#13;");
		sbAux.append("7GStEQlmZzDM7DwSzS+wEHNB8Co59geR58SUyGUnCtwPCKpCjYxxkxod+gRTEzjc83B4cpyPINuJ&#13;");
		sbAux.append("+R+HQ8iyYQBZ7UE6S99TTI8weBoxzIgUcC2GGa4RhjQGK+9DrOoFDFA8hZ5y6+GnNQ2WygMRGZCK&#13;");
		sbAux.append("W9cffV7Ozvdd4pvP861yfg+ir/JAfL9oCjGkINRpX+uBuEoRQBQ6yIdQGCOAKHxxUAggUhBqojDR&#13;");
		sbAux.append("Y7QwkCbBkzJR4zuG8FBHIY1gIhWj+yh8kbn+rVF6nuILYWzmPGzdsBvJsXkoyB2HrBG1GJ5cwH4X&#13;");
		sbAux.append("S+WVVaAsrgAHDS+EsDAWpFCPEOnFiJTdib4Se9Bfdh8ipLZjoOxWxMhtxmDFDRisthGDtTdhaGem&#13;");
		sbAux.append("QPp7MdTgMBKMzzE1uMjCySWkmF9koeY0slnKneOwV/AveS4HkOt6CCPdKDQdaoLJ86AAE8+0eNjK&#13;");
		sbAux.append("ZSGPwKHpG5kO7NruALIsdyOz2x6kdT2EpP/X3lfHR3ltXfPWS7FCkpG44Fq0QIGiF/dAcCm0NHiA&#13;");
		sbAux.append("4JBQpAUKxd1dixcrlkBIIMHd3bW4rXevM9lhmptC7td+f7y9HX7798w884yEs2bttffZZ588cQKe&#13;");
		sbAux.append("XWiTMxJtsm9A26wb8a3fFmGfCLTxOYrGthhUc1uOetnno3zmMJTN1xJ7o09A+2QSOIy4uR6QTKTt&#13;");
		sbAux.append("YJxbCiYGUbIBxO7nHHS6DboPzer+JwDSemYOOt+LukWbJ7BMg0tulJXIJjQCiUeCiExFY5cOrvki&#13;");
		sbAux.append("IxLMGqFxrRcTjtRrjMI0ZHfuIKLf2URm02Yj8tdYdOswCGG9R6JXzyECoL5o0zbEhPEeabxR0F4K&#13;");
		sbAux.append("dXN0Rl3vYahrmYVGlrVo6LIJzd0j0NDtFzRzXyG/7BX4yms1vhYG+sZfmCfTLrTOshdtshwQFjiC&#13;");
		sbAux.append("DrkOS0R0EJ3zHTJM0aPwPmGOPWYWnYDoJaK6N91R6dh4MMUaMNF4n+f4XC8zeSrft1gsuhaNQ/ei&#13;");
		sbAux.append("u9G9wA50z79dwnVxXQKetjljEJwtCsGidVpnjECwXyS+Fvfa1n8vGrhtQk2LRJQ5FiMwzwQUyxSM&#13;");
		sbAux.append("pXN/TVjKznHmlgnsi6DtcxL3nkwMomQBiFTGykQNnQmg5M6Dae20hvJ0HRx0Dj5Boz2CtLsZgUWX&#13;");
		sbAux.append("RfbhjDsBxDkvAofnCDoyF/USwadLnMlIvJ6vZckGhbQCRUGcuIB/2pSZiPh1F/r3/lE0UBh69Q5H&#13;");
		sbAux.append("aA9HeiGbfzbYP/FBnnQlEJi1M+r5/iQAmiGDsBwNXdehqecGBLkuQyP35WjssUoer8dX3lsERNFo&#13;");
		sbAux.append("5b8bbQLi0C7zHrTPHocOOXchJPduicp2o0v+3ehWaA+6Fd6P7kX2oXtxhy7irHrPL3ebiVGChZOk&#13;");
		sbAux.append("fco47vMcn+tefLcBT2iRWHQRXRX6+S50yxeJrp9tR+fcO9BeXGabbDFolVm+Q0C0sM9OdMgs38NP&#13;");
		sbAux.append("3JqAur5lFYI8l6BOwDzUzjENhb3aY3D4ZMM8LBwkSNgbk00pCCCykvYUSgpEtGQBiC8kMnX1J4/M&#13;");
		sbAux.append("qSRHAynr6FQGS0q1SxmFL4U5AURx7Lz2XaMuMgqBRmbiDjucuuB9uitey9fyWjIjozpqJrowXW+m&#13;");
		sbAux.append("WXAFkEZjBtyTp2Dz+i0YOWK4MM/X6BnWBn37dxAd1Bo5M2eH7WN/ZPu4OGpnllDedyzquM1EfQFN&#13;");
		sbAux.append("Q7fVaOa9GnVcl6C+uK8Gtu1obBdd5LkTLX04cDFoKwPYLvN2dMgqUZiI6pBcYp9JOJ1/p0RLu9D1&#13;");
		sbAux.append("cxHVRfYaQHQrtkvAEWOsx5cxJrNMM8CRI8/xOV4XWnQXOgtwOhXahS6FYgx4QvNEolMu0WE5diA4&#13;");
		sbAux.append("Swy+zrgLX/vESfgei9YioNv47kDbTJHyPZeiWaYVqOo+FVV9p6OQPRTBzcISdvvRClQV0+rK3gSi&#13;");
		sbAux.append("ZO8XxkYL2uaXA8dQ/j8FEI+cxFQAsRyDYFHA6Oy7tnlRbUPQcomyn58fChQoYFq7ECzUZJqZJoC4&#13;");
		sbAux.append("2pRRnc6Dqe7SdIMzgNSVbVi3HpMnjUVwuybo0fdbDBjcGaHdWyFXtqywpfSG/4efo0bm7qjnNw6B&#13;");
		sbAux.append("brNQ1+VnAdAqYZtVqOOyGPVFAzWw7hQARYs7i8bXXjvNL79NQKREZNsQInrIACjHFnTOIwDKK3ol&#13;");
		sbAux.append("f7S4sjgBUaxxQ12/cICjW7FoB4ji2YjG+wqerl8IgxWJEfDEIKRgtERd0QY8XSXSChHd0zbbdnGf&#13;");
		sbAux.append("Ufgm4DWAGny6UYT/Bnzjsx4N3RchOOdaVLZMQRX36Shm74O6Vdrjyo2buHnrTkJX3OdPJTJ7/sK4&#13;");
		sbAux.append("MgUQe2q/ePX/ACDnNfMUwNovSGtwnAvGkjLmf5yX7HAwCSLtKf22RKKG8wRGvnz5TMKQ7kw7bjAH&#13;");
		sbAux.append("xGI31UAsxtdZ/+SkGbhMeuLkSejStbMwkLiv0NYYPLQvviicB9Y0bghIlw91CvVEZa8haJRxqbDO&#13;");
		sbAux.append("cgSlX4n66RahqW0lGtk2opGAqLFtK5rZt+Erj0i08IzA157bBEyb0DbjdmGiCLTPss3kh0JybENn&#13;");
		sbAux.append("idC65Ba3k2e7gCAKnUUEsyy1S6Ed4paikjQ+z+s6ieYJEc3D6YqOwj58ny45hIGyRprPCfaTz/WO&#13;");
		sbAux.append("MN/hKw/H/Wbu6wXoy1DfvhgNvReZxGhtj1ko5yNuv3AjbNyxE7d/e4wbN+/i5vVbpu2N6V5rdoR+&#13;");
		sbAux.append("gEdPHuLhs0emsz+B5Oj24bBkTabqFlDUL1qvzIHT+bC3AUjzL3y99gdKLoC0XTDdVt68eZErVy6j&#13;");
		sbAux.append("dRidEUBkH+1sRrdGgc2EpWqutwFo5ao1mDFzNjp0lu8SFi5ACsHA73uLcC8E17Rp4ZUmK6rl64Dy&#13;");
		sbAux.append("3oNQP+MicWMrUC/DKjTOsBQtbKvQRMJigqipuLJmdorrrWbwWnpsNSBq479TQBSFDplk0DPLQGfb&#13;");
		sbAux.append("JgMegVBhDAOkfDsFFDuNa+sczyp0TaEFY3535HleF5J3BzrkFdcozNMxd6QBTxdhnpAs24XxJPry&#13;");
		sbAux.append("3YZvvDejhddmfOW5CS18N6OJxzrUEwDVsy1BA0+CaCHq2mfJ3zQAhfIFYdmmrbh1XwT0rXu4c/0u&#13;");
		sbAux.append("Ht29L8L3hVmUyOXRvz15gAcCoIfPH+Ppy1dmMYejBVoyRbSqcq6WICNQsJKFdFXEm4zTGM51OqpJ&#13;");
		sbAux.append("tJFUciZTCVpqH06aFixY0LgvimvtZqZbMhBAFNZkOH5ucqLEpct+xuIlP6NDxy7o01fA2KML+oZ1&#13;");
		sbAux.append("E8YrC5c0aYwOKpPlK1TwGYgg/3moa12OBq4imkVMt3Rfg8bWNQKg9caauG8UAMnAeWwxIGrhuRXB&#13;");
		sbAux.append("oj9a+wsL+UeaASaIQrKIK8subkeA1OmzKAFFlDl2EjB1zrvTHLvkixZQRZsjH/P5jp/tENAIUAQ4&#13;");
		sbAux.append("7XOKtuLrhXk6yft1yBQhkdY2fOsrwPXaYr5Dc49f8ZXPJjR2X/s7ADXwWoBAdweAcmatiulLl+Pm&#13;");
		sbAux.append("vccGQHdv3MNv4s6ePXiEW9euiqC+j/uPf8P9eBA9fk6X9soxA5JcAOn2BFx7zoQdM9EcPC3aepM5&#13;");
		sbAux.append("Z4N1HooCPLkAorahkCb7BAQEmHpoimoKcL5eN4AhgMhM1FKa5NQ19m+yBYsW4pd1a9ExJNREYd17&#13;");
		sbAux.append("dkOPnp3QrFkgXNKmgsv7HijgEYhK/v0R6CcayCphsGU1vrKvEIZZLe5rJRrafkEj+1rRQRvEXSiI&#13;");
		sbAux.append("thkQcR6qlXckWvvIAPtFoF2AY7A7SphNgR2SUyIlJ+uYK9pYSO6YBONjPtc+RxTaZd9htE4buqys&#13;");
		sbAux.append("kSbz3d5/qwFPa5+twj5b0dJz82sASWRIAAVZfkaQ/fcAogvL6FsOw6dMx/U7D40Lu3/rN9y9ftOw&#13;");
		sbAux.append("0JUL3Kz3ntli/J6A6Len4tKesRnrS1MWy4n8ZEdhvLFXD+esCCAOVHKaKzgXlmkGmO5P+0+/DUAE&#13;");
		sbAux.append("B2fjWQPt5eVlwKNNzLUnNa/jfYJLy1mpt5KzcmTu/DnYtGUzunaT9+Dynl490b1HZ7Rt2wxu6dPi&#13;");
		sbAux.append("0/esyJa+HKpm7o9avlNR17ZQRPNKk/tpJiF8Q8syebxaQLQmAURN3B2uzIDIfZu4M4cmSgwkRkbt&#13;");
		sbAux.append("suww1j5rlLEO2XYmaXyO17WVyK6NvM7keAIENL6bjAX7bMa34rYInhbuWwyICaDmcq6R/RcDoLo2&#13;");
		sbAux.append("Ef0ei1DPewFq22eirHd/+HqWQt8ff8K12w9w7fptA6DbV6/jwe27uHz+HO7du2PaxNyJB9GDJ9xF&#13;");
		sbAux.append("4DlesNnZU/xnIpp7UdFF0GVwYDlAiVdFJGXaHEGFNMGn2iY5RWVMNBJA3t7eCQzIfJSG/2QhshRb&#13;");
		sbAux.append("3FH/kOG0CvLNAJqIOfNmYsu2zQjvNxCdOgsrdu+JXn24PUMw7G4ZkP59C/xSFUONnANQw3ccatvm&#13;");
		sbAux.append("IEhYqKldgOMqgtS6FPUtKwyoFER0ZaqHmtu2GFMgfSuMFOy9zQCJSb42AeLiMu4QUEQlWNvMO//N&#13;");
		sbAux.append("eJ7XBQdsx7fiDlsJCFv5bEErr1/xjedGfCNgaeGx8TV4xIxLFQA1dP9FmNMBoCDPRQjymo/aEoUZ&#13;");
		sbAux.append("AHmXQafw/gZAV67exL2b93Hz8lXDQDeuXJbw/pbZo/62gOjuo/v47TG3GX+GZ0+EhZ68St7CQr2x&#13;");
		sbAux.append("WaV2TuUAcl7rbeDRakAdTApvht66w8/bwMNryToEUJYsWRI2wNP2dhT07LTBJdBkIbovRohaxvo2&#13;");
		sbAux.append("AM2dNwObt2zAkKE/oW27TugSKpqqf7i8dxt42S3I8JEVnh9JJJZPAOQ3EjXtMxBoWYiG1kWonW4W&#13;");
		sbAux.append("6lkWi7ZYajRGA/uq37FQExnMJlY5SpTWzCqMZI+IZ6NIcW3bDSNRI6m19osyRuGd2Hie1/A1dIuO&#13;");
		sbAux.append("KG8LWspntZQo6yv3Dcaa2zYZ8NKcAVRHvifZM8hzgQBoLmq6T0EZr3BkCqiAtj37GBd28dI1o4Fu&#13;");
		sbAux.append("XLpiNNC9Wzdx+7aE+PcERL/dMSC6/4jbjD/B08eihR6/fDuAnPdH5X1OVpIFtFH42wBE3cOjc3NN&#13;");
		sbAux.append("5oCSCyAKaIbsBFCePHkSZuW1abnuosg5NOamtAVf8no4TsS8+TOxafN6jBw1DsHBIcJC3SQKG4TQ&#13;");
		sbAux.append("ru3h62WDa0orPD7MhaDPv0O1gOGoZp+M2m7CQpZ5qJZmqrCRDIrVAaL6tuXGXTR2X2cGjyBqbPlV&#13;");
		sbAux.append("bDOaWshEdGmRprxCQfStT9TvLNh3p7HWftEJxsd8rpX3DvMavpZAZKTXQsR7C/s6NLevN0YN1iz+&#13;");
		sbAux.append("sw0Tev+KBh6rDYBq2yQI8JyHIO/ZAqBJKOPdF1mzVERwt54GQBcuXjVRGAH08vFTPLp/zwDoxl0B&#13;");
		sbAux.append("UTwL3Xv4yADoySPRQo9eJD+RqDdmpNlJlas7OXAEE1mCFYscFGaaabpqQwdUSyzIXGw1R7fjDCTt&#13;");
		sbAux.append("C5148xa+N+fGKKA5fcHr+Dpt9kDWYfaZLkybdJKBtDPH2wA0ZuwwbN+xBaPHTJD3G4DOXXoYHRQW&#13;");
		sbAux.append("3h15P8uB1O+mglfKnCjq0RwVffqjkmUMqrtNlwGZ7QCQbb4BESMcBxOtECCtRgPLWjFGZ5uMNbVt&#13;");
		sbAux.append("TrBm9i0Jro2s9CYj4NSczxOMhm1s6+T91oitRVPrWpNWoDW0rjPinqzY1FdY0WclarjNROX0E1HN&#13;");
		sbAux.append("OhE17BNQ1qcvfLxLIrT/IBPGnzt/GRfPXMKdazeMC7t/W5jn1g1xb9dx5dY1XJXjzbv3hEgeGw3E&#13;");
		sbAux.append("JR3JWtajG+w66yHOnxBMHDC6FIKCkREHl7XKGrYrqDQ7rNljFonRBb4NQGQZhvB2u91EWVoNQBfG&#13;");
		sbAux.append("92E2m0fOfenyHZ3requInjIeY8YNRUTkRowdN0kANEjeS/RZj16iiXqgUKHcSPVOSgnlM6GgrR7K&#13;");
		sbAux.append("+/ZBRdtwVLdOE+DMQY30PM79PYgYLlupidYYEJlEo1gTqyNXpMbBdwBoa7xFJHl0gGdbwmMHcLa8&#13;");
		sbAux.append("BpAAhwBqKqBtIoChUYvRCJ4g2zI08hZQey9BDcsMVHYdZ8BT02Msyvr2hK9PKXQb+AOuingmgC6f&#13;");
		sbAux.append("u2IAxEiMUdiNGwKcePDcEFd298FD0/k+2QBy3gLKef8JTTBy7oTzKGxExVWhZBYCibqEIThnxSlq&#13;");
		sbAux.append("ta8iB5vMwaNWFr4JQMw7cdUFAUQ3patDeD3BSIFN8BCkZB1+huaadHXqmwA0dvwQbN66BpMnTUfv&#13;");
		sbAux.append("ngPlO4WZaIytX0qWLIyU730Al/e8kMelGsp5d0NF9x+NG6vnMQeB1lmoY5stNkf0xXyHxrAqiFaJ&#13;");
		sbAux.append("PlptWIBskJArEmsqukVN9QpZKamjEeLum3933gHCzeb1TewCHPtKY41tq4w1FKOop0ut5bIA9dwX&#13;");
		sbAux.append("o47HXFRzm4SqlrGo5Skg8hqN0r7dkCXzvxA+fCQuXL2NM2cvGgARPIzELp07iytXLuHKzasO8IiI&#13;");
		sbAux.append("fvj0mQnj2XY6WWG8brGUuA5Et03k89yHlHNlZCmtHSK7sDyD7oVAICMQMHR51E50eRTAbwMQryeA&#13;");
		sbAux.append("OA/G0lcFju6vQfZTwawlHHRdBNNb1+8bAA3G+o0/Y+aMuQKg78Ul9hf90wv9B/RFlSpl8cn77yPt&#13;");
		sbAux.append("O27InLY0SnqFoJLnYFT3mGR0RD2Peb8DUR3rPKOHCKIgy3JjGuIbIMUzxGtb+zswJWWqafRxk3gQ&#13;");
		sbAux.append("Okxck22F2LJ4E+YT0NAIHrrU6ulnoZZ1NmrZZqCK21hUs41CoM9YVPcajhKenZAtawUMnTgFZy/d&#13;");
		sbAux.append("MABSF2ZAdP2aBE4XDICof5iJfhYPg1eOPsLJ10BJbReUlLtLvP8p26hwIpSZY04zkC24Iw9zO8ok&#13;");
		sbAux.append("bwIQXeNnn30Gf39/s6BQJ3TpAukqdYpEAUOdpevx39rDSAA0fsIQrFmzEPPnLTYA6tF9kICzFwYO&#13;");
		sbAux.append("Ckf9+tWRJuWHSJ3iU/ilLILiXsGo4PUDqriPRR2vWSaaCbTOMCAKlEEKtMwVEC0QW4S6IlqZe6lv&#13;");
		sbAux.append("W2mMjGBCffsKYxxssoczuJIyRnXOpueNi7SuNMBpJEAxJqG6SSuIGT0mLrV6+hmoaZ2OGtZJqGIZ&#13;");
		sbAux.append("heruI1DLd6T8DYPxuVswvL2K4aepM3BSmOf8hSs4d/I8rl24hOsXL5tM9NWrl0Vg3zA5IEd78tcA&#13;");
		sbAux.append("SlYeKDEbsciI0RiPykK6ca72luZ5ntP9vMhQbHqpBfN0b8zl6DYKbwIQ63syZswIDw8PwzjOqyx0&#13;");
		sbAux.append("saL2VXQuHnPunfjHABqHCRMHY/nyOVi6ZAV69RhkispCQnrh+x/6C/AbwjV9aqT6n7Tw+qgginm2&#13;");
		sbAux.append("Mst8KlpHmDC4rucc1LZMj7eZCSAKtIg7szhA5BjIpWYys4Ht53hbKqH+zwKkZfGuZqVhqqSOqmV4&#13;");
		sbAux.append("dD7P2h6+pwM8i01aob4wH83BgouNW6VwrmWfhuq2CQKgEajm+RNqeA/Dv9zCkTtNI3i4F8H4uQtw&#13;");
		sbAux.append("XJiHYfyZ42dx/uRpnD1+0rgwRmF3lH24w48TgJKVB9KqtP/kpkXanEMj2AggMoROoGplo26F+SYA&#13;");
		sbAux.append("MYQneHx8fIxbpM5ivY+uSyPrUJzzsTZW0HLWt4bx8QBasnQ6VixfYwDUp/eP8v16mu5l7du3hIfN&#13;");
		sbAux.append("BWneSyuhfF584dES5b36yX/+UFS2jkNNGZgaFofVtM5ETcssE+ITQHXcFhrTwXQwg2OAG9gXOyw+&#13;");
		sbAux.append("f0ShS+Gd1JFgqWdfnnDU88ZF8v1siwQ8C+QoWodC3qQVFgp4Fxi3Gugu5jHNCOcqluGo6iEazmsI&#13;");
		sbAux.append("Sqbrjkwf1EThwoFYvnkbTpy9bBjoxOGTOHfilLHrly+ZyVTOxD95ZTZHd2z488rR4SNZAGI1otaA&#13;");
		sbAux.append("KAMljsh0m0znjd+cXRr1EVmHmocspCsqdJXFmwDEyMtmsyFHjhwGKDr/RhfFvA/F87BhQ+PXfDm6&#13;");
		sbAux.append("j40YMdwI6QUL5iWE60kep3CidxgWLZqB1at/EQANEFb8Hh06hhoAde7cBr6edqR9L70AKA+KejVD&#13;");
		sbAux.append("Oa++KOP6Ayq4jDXRGK2GdUo8iKYbINUWIDmYaG68uHZYkF0G2S46yX0e6rvPFw3Fc4vMHNUfWT33&#13;");
		sbAux.append("pcaCbK+N83F145mG4GlomycR3/x48Cw27Ec3yrxPHa95qO0j39NzLCq5D0Fl74Go7BOOoumC4fNu&#13;");
		sbAux.append("WTRtHIo9x07gzMWrOHX6PA7uP4QLZ84KeK7gkYCH7fCevXhqtmZ5bhZCO2bjXzx7iedPXyR/KkN3&#13;");
		sbAux.append("8HHeyM15LZGyFRlHAcfneY5bOrEYXrcQ1y0ICBCKYeoa3XCOCUotWiOoWAP03nvvmbJWLR8hSLTb&#13;");
		sbAux.append("x5gxozBpMgvHpmHW7GkGRFOmTnCAZMp4symtPuZx6tTJmD6DDRimm+snTBxjXrt581b5vF7oG9YP&#13;");
		sbAux.append("od26oGevrviuXx9kCciID1J8AusHOVAyoIUJfcvLr7hs+jGoKvqnqolsJpjcCq26bRJq2Kb+mxm2&#13;");
		sbAux.append("8hBX5yGaxFN0k+dMh7nPQl37HCeb+zsj2IwJ+JzBSMFel+YyE/VcZqGeq5x3FeZxW4pAtyWoZWHY&#13;");
		sbAux.append("vgDV3GejWZ6lqOQzCgXSdjaRV0nftsiepjoyZSiJFUs3I3JnNA4dPooDBw6JHcDF8xfw+OGjxJtb&#13;");
		sbAux.append("J9lLKFmZ6MTMknhzFgWO82t010OCiG5F943XklVlIq0oVABpyayCjMCxWCymsJ7JS5puMktGWrJk&#13;");
		sbAux.append("EWbPmW5ANG78KBHFo42NHTcSI0cNM0c9T7BMnDTWHPl43LgxCVUC1GgG4OF9BECd4gEUhs/zFcaH&#13;");
		sbAux.append("KVLDJ20+FPFtIAPQBSXcwlDWMhoVXCehous4VHYdYyKcqpZxAqLxRm/QGO7XEKsWb9VFN9XwEDB5&#13;");
		sbAux.append("TjP2GkCJQfTaFEAKKBPtxUd8da1zUDPtFNT+dBrquMZHgCLca4u7rO62QL7bDFSQzy2aYSAKu/RE&#13;");
		sbAux.append("pWzhKJO1A9zfLQb/1MXRoHJbHIg9ZgKd3bt3my1E2UiDaZnkbp2e7LXx1DO6l7sWmClY9L6ucHSO&#13;");
		sbAux.append("xK5cuWJWUGhDBc0gq7tiaE8AURvpLs/KVAQd63/SpEljCslYbcgcE7UQNRXd4KRJE4xNnEjRPM4Y&#13;");
		sbAux.append("2YYMM3fubPMcH5N5aGQkXkvwkL0my/OsjWYjctY59Qnri67dQ+W7dcd34f1QtUI1vJ8iFWwpsyO3&#13;");
		sbAux.append("tTKK+wSjqDUUZT1/RNHUg1AmnQjSdMNRMcMoAyIyUC0BSaDXdBOp0b1Vp4uzTTdWU8BSS1ihtodD&#13;");
		sbAux.append("nyQwibBFUhbkpGnU6jDac5tvjoGiucxjeY/aooNqWeejsgjn8i6TUcplBKpnnIAC6bqigEtrlMrS&#13;");
		sbAux.append("HrlsNWH9MB+qFGuGWeN+xomjZ8wWmdzf9uLFi6Z0RwOkv2Rps0ZUzklEniPDOE+0EmDM/3DPCiKZ&#13;");
		sbAux.append("205eu3bN9CLkZKhmnVXj8KhTEVpVSADxvAKIScJMmTIZDcSwn+/B/b9YUMYSV7NdwaABRgMROLNm&#13;");
		sbAux.append("zTCgcWihURLmD47XO6+NADJubPpUY1OnTDAg2rhxvZnnI4C693Rosn79+iOoTgNxYWnwSQorstvL&#13;");
		sbAux.append("oIhfU1TI2QPVcw1FKXeJyGzDUEmissrW0ahiE7dmH2/mmWi13aeiumWyw9ymmkwwNZLJy4hx0Cm4&#13;");
		sbAux.append("OTlLwZ3U0QCGqQG3+Y5rBTBGqMcf6wg7BXrONYCs7j4DVexTUEm+QzkJ18t4fo+S3n1QLmtPlM7W&#13;");
		sbAux.append("DhnTV4DLuzlRumBtTB41HycPX8Su6DizYQ1X3SgxJJYmfwpA6orUdRGhzud4nzsNUpQyOUg9Q6PA&#13;");
		sbAux.append("ZWjNfI0WfWkRmQKH7kq3aVIhzfOqkzjDTvB89NFHhh34Czl16pRpT8d5OM6RlS9fTlipvjBZZ7nm&#13;");
		sbAux.append("OwOaUaNGGHAsXrzQgIXAIcDIOuPHjzWsxOeNZpomz4mLW79+rWG13n3ku/QUEPXog/CwAahVMwip&#13;");
		sbAux.append("U7rioxSuyOFbEjk9K6NMntYom6szKmUPQ1X/oajhK+Gx1whUlRC5svtPAihhJLeRqOQyKt61TTBZ&#13;");
		sbAux.append("YIKoltsME/LXTojYFopeWRR/XOh0nG/YhNfUtMwx1zPKqyXswtC8put0VLNMRWXbZFTymIwq3pNR&#13;");
		sbAux.append("1W8SqviNQXnfoeJqw1Bc3G1h71bIkqEqLB/kgyVVLlQu1QAzJi7E3tjD2B2zB7Gxe8yP3dmLJM7l&#13;");
		sbAux.append("/WkX5iyiFZm8T3+pTS05mckEoeOX+3ozOA60sg/BoZvXUTxruxbtfqaaSLuXcaM4ZqBZSEbxzD0v&#13;");
		sbAux.append("uAdqVFSUKW6jOCdY2rblbHxZAVQptGjR3LASGYjPLVzIebg5hp2mTZtiwJTg7oyA5t5nw7B23Sqj&#13;");
		sbAux.append("hXr3ke/eux+6de+DsPCBBkBurp5I+YEL7K7Z4eWaH75uxeGRpgSyuwaiiLUdirmKLrKIOLX1Qln3&#13;");
		sbAux.append("cPzLY4DJWFcWN1fBOgQVLQ6W4jSC0UiWiSaxZ1ybdRaq2dRmvja7AMQ+FVVtU8REqDMMt42PN9Fd&#13;");
		sbAux.append("VomqbKPls35COZ8RqBgwAhUyDUUZv3AU8eyInG6NkSl9NXikKoa072aV750X9QO/xfQp87E9Mhqx&#13;");
		sbAux.append("cXsRt3ePmc9Ud8Vj4i3U/zSAyDiJhTTrgthJnoyjUZQuNTbbKA0ZYgDADDRXTGhzKi0AI0CcewPx&#13;");
		sbAux.append("WtVFPK8AIsN4enqaBuMM1yn2CBwKPh7pHuku2YJPKwTIWgz91cXxs5mxZtSm5SWaHyKAZs6ciNGj&#13;");
		sbAux.append("h5qGm/PmLUDvXv3Qp/cAAXqYfI/v0aJlMNwsHvjwg1R4R6KxEl9UQfbMxeHtXhD2tIVFjFZB5tR1&#13;");
		sbAux.append("kC1dfeTO0BgF3L5GYVsbFPfohBIeoSjlKaDyDBOX0h8V3AehoscPqOwxVJhqmGGrih5jX5vn6Hgb&#13;");
		sbAux.append("iYrCaBW9hqOC5zCxoajgNdhYeXFL5T0HGpCW8foOX3j2RGH5nPzWtsjl1gJZXeohk0t1ZHQtB39L&#13;");
		sbAux.append("CXhkyIPqlZph6OBxWL36V2yLiMKqtb8gZs8uvJCgnGG5BkXO01baE+EvYyC+ITUNxSa1gtbyaDhO&#13;");
		sbAux.append("4BBMTPyxfpnRE+fCdO26lp3qpm8Eitb2OG8IxySjdiAjENKlS2cmUrlPBsFC4BAs1FrUXIye+Fij&#13;");
		sbAux.append("iD179pilzfx+1EtFihQxTEYNRZBr0pFGFzZr1iRhKk5nrBK2WiwA6o8+vQbJd+1nANS9R19kcHHD&#13;");
		sbAux.append("u+99gBQp3kN4v0Fo2aoj6gS1RIF8FeBv/RL+Mlh+LmXhm0GOn5aHX5oK8EtVGb6fVEYxfwGTX1uU&#13;");
		sbAux.append("9AtBKZ9QlPHp5kgF+Io2ETdT1r8/SvsPjD/2l2M/lA4Il2M4SmeUH5F/L5QK6CHPCcMFdJNjKL70&#13;");
		sbAux.append("74RSYiUCOiJL+vrwTlUVLu98idQpCuDT9/PBx60ocmcti6IFK6Nn94GiC5di5aq1WL9pM85duWSK&#13;");
		sbAux.append("41kgxtUWT549/beIi7MLJI5kRWHawsN5j3XnkJzPEzh0G8zucsC1vYqWZHDwueSYgjdt2rT4+OOP&#13;");
		sbAux.append("zX1GTWQAAkdfQ7ZSwFAX6R7xuqmdtvUlMLkSNUOGDOa92B2EoKHboitj51gaXRqPBPaxY8eMIOR3&#13;");
		sbAux.append("PX36tGEo6iV+BteMMafEyV2+P38ETBbOXzADU6Y6dNHGDVvQvGlr9O83XCKwYejeVb5rWH+kd8mA&#13;");
		sbAux.append("j1J/jBTvpEDv8DBMnjkTA4cMQ+9+ZKiuqFe/PSpWbC7sVBcFP6uGbBnLwsOlMNJ/mBuuHxWE9aPC&#13;");
		sbAux.append("sH8obu/jkvBN9S9kTCsslr6WuMA6yOpaH5ndGsYf6yOrWxAyW+S8Wx05BiK/X2Pk8g5CNlt1+GWo&#13;");
		sbAux.append("CO80ZcR9loZX6pLwTPslXD8uBJdUBWBPXwg5MpZBxXKN0LFdX2HcaVi8ZAXWrN+ArdsjcfDoEVy6&#13;");
		sbAux.append("cQ2/PX3M7XfNpk9P//x2YUihNJUUXRFU/IVzikBzNQSQ7h3Gx5wh52w5pxuYr+GAf/rpp2aTWwUM&#13;");
		sbAux.append("r2cdjyYOE2/jTVM20rViZCICiO/JMg6GmQogsg3dGb8bwcIwlAAjsHgNtzEgkAgsvu7o0aPmeopk&#13;");
		sbAux.append("ujhul8B1ZV9/3QLtO7TCiJGDTe3S+nWb0KMbG24OQZdOYfiu32D07fMdcuTKidRpUxoABbf/Flui&#13;");
		sbAux.append("tmDxiqWYPGc2ZixYhDHTZmDAsFHo2mcgWrfvgYZN26BKtcYoVbouSn1ZB0UL1USerOWRyaM4PDOI&#13;");
		sbAux.append("60uT35gtTUG4pf4crmmKiH3uZFyTVkAsH9J+nFMsO9J9kh0Z0uSAzSUvfNwLI0tACeTIUhLFv6iJ&#13;");
		sbAux.append("mjW+QqeQfuKKp2PRopVYsWad2GosW70ch08dxqmLp0w9D8GjwOHxyUv8dY3GKaBUTN24ccMMEicj&#13;");
		sbAux.append("dcpBB141jCb8yDKc7Hz//fdNvoYDTtFLN8bFgCx0Z5GZFuJrhzKCRhtWqRZyTiKyjIMz8HRhHHBq&#13;");
		sbAux.append("IAKE+5excz7ZhdFfTAzBFGsaZtJ4f+/e/eLOjuD48ZMCqJ3mmsOHj+L8eUZxZ7B27XphIcdq1y9K&#13;");
		sbAux.append("fI7SZb80JSLjxk5Gp/a9EdZrML7r+yPat+mCfhKJFSyYHyneTYGP0ryHFt82x7Gzx7B9VxQiY3di&#13;");
		sbAux.append("54Fd2BIbiTUR67FkvegoAdbU+fMwZso0DBs7Ab3CB6Frj+/QrkMvfPN1ZzRr2g5NG7VDs8bt5X4I&#13;");
		sbAux.append("KlRsjPIVmzisUuN4axhv9VGlehNUq9UEtYNaCDBb45vWoQgJFc0YLt/x+2GYPG02Jk+fhakzZ2Hu&#13;");
		sbAux.append("wgX4ZeNa7D6wG+evncOdR7fx27P7ePjioZlJ133kaU+58vT5XwAgVdparsHHFKXsTk/RSSbQ9Vlk&#13;");
		sbAux.append("ETIFz9El6fIa3eOCYGHpaapUqYwrS5kypVlJwSQgM8l8ntfyfQgozUYTONoHUZONTBiS1chorIcm&#13;");
		sbAux.append("y2XNmtUkJSmICR6yC8GwfXuUAD5O2CkG+/YdMKHphg2/msd79uwzFidRB68hmPg80/Z0ectXLsMP&#13;");
		sbAux.append("Q74XkBZFxoAcaN64jQFQ547yPXqEY+SPI1BeIrz/EfYhA1WoVA7RcTHYHBGJtZs2GgBtP7ADkfui&#13;");
		sbAux.append("ELFXjmIRcTuwLTYKW2O2Y/n61Vixdg2WrlqBBeJOZ82bj0nTpmP0+En4afQ4A7Aeia3fgATrO+gH&#13;");
		sbAux.append("hP8wBAOHD8OwcWMxfsZUzFq8AItXr8CKDWuwYdsG7NwTJSxz0gBGFA0ev3qEu4/v4Pq9a/LY8Y9z&#13;");
		sbAux.append("WcZevTQ1PWp/GkDOWWW6MUZYnO3mmnG6CIpWRlwMo1m8pX0JtV8iz2kkRWPUxbVjXKNF8csoSnWR&#13;");
		sbAux.append("m5ubWV1KMHFujCG+Zp+1c6smEqmp3N3dkTp1agMglrWysQLZ7sMPPzQNFrhSlpOgBAUZJzp6l2Gc&#13;");
		sbAux.append("rVsjhKU2ies6bgDF5yMjd5jn9+8/aK4loHguQvTBrj17cez4GcwTsVmnZlPky10MDQKbILRjV/Tr&#13;");
		sbAux.append("1Ud0RWlYXNLik5QfyPcOwomTp3Ht+h3sP3QccQf3I/bwvgTbfWgvdh3cYyzuoNzfF4vY/bsRd8Bx&#13;");
		sbAux.append("jNkTjcjoCGyO3IINWzbil183iP2ayDYk2M44ec3eOMQeENAfO4QT507hwrVLuH73Jm4/vG3A8gQP&#13;");
		sbAux.append("CRsDnmdiz1/DRdzUU2NPnz8x9uzFY7Nz4SszNfoc+CtcmKpwZpyvX7+esBCQv3Qtn2AURG1BZuJG&#13;");
		sbAux.append("Jaz+I4g44AQNmYXujaCiO+BzZBmKVmaO2RRBwURm4n1dpkwdxZCbzKaaiZETAUcQUk+xoJ4RFQvT&#13;");
		sbAux.append("2IWDLpLhfcOGjQXAfSQ8n2nAc+bMOQMcshLZSd3awYOHDZh4TUTEdsNGR+S6mLg9IjBPiJbag9hd&#13;");
		sbAux.append("h3HnxlPs3BaHHiG9UbdGIBoHBcHfy13MjlrVKmOiMEdc7AEcPXxBXORlYbFzOH76HE6cOW+Oh4+f&#13;");
		sbAux.append("wKFjx3HkxHEcO3USR44dxvGTx3Dm3ElcunIet+5cx/3Hd/FU3MozPE0Y4D+yxy+eGHv0/LEx3uf5&#13;");
		sbAux.append("Z6a467kBDgH06OUj46pYevHw6RM8ePLYHF/Gz6C/ePUcLwU4L189gmMz1QdI2FT1zwKIk2fOTERB&#13;");
		sbAux.append("yl+31jEzq8xcCjvM6zYBZCiGz9w+kteRPah1CBxtw6uboxAYDKMJJjIJ3ZzVajUAoYviY0ZJBAaL&#13;");
		sbAux.append("5AlIgiZ9+vSGfait3nnnHaOtuP6d3Tl4LVmoVKkyKFG8ND7LUwDVqtZCxw5dRMtwkPfjwP4jiImW&#13;");
		sbAux.append("X+/ufdi39xAOHTyGw8IaPB48cBT7DxxB7L6D2L3noIDuLE4ev4SIzbHYE30Il89cwZ3LN7D252X4&#13;");
		sbAux.append("rmc3jB46GIf27MGV81dx5OAZxEQdxfEj13Hs0GWcPn4N50/fNHb21FWcOXkJF89dxbUrNyWCvSGa&#13;");
		sbAux.append("8lZ867h78iMVPfL8EV68fGIGNGGRebJSKTD1yFwZSnssAGEuh+rmkYTjXPT3QITN0xdOs+cJPupl&#13;");
		sbAux.append("vHQmcLjR3K3447M/ByCtY9bsMlmIITEjGDIOAcKOX1rAxZl1sg+ThQzjqZN4PUUtw3yCje6IjETj&#13;");
		sbAux.append("fBaz1AQRWYbimCAhADJnzmxAwekKAilFihQGWAQTXZWrqytcXFzMkUAieBiRkZ3IRnSFefPmFxYr&#13;");
		sbAux.append("jfz5PjdgKlqkhDBaHaxetc5oH4KIror7YpCVjh87jYsXL+OEuKxdu/ciIioGMQK23WJHj5zB7mgB&#13;");
		sbAux.append("XuwR7Irchd07YnBe2OTEgX04fmg/Lp89j9idcTh3+hqOH72Mg3uF7fadw6nDl3Hh1A2cPXkdp49e&#13;");
		sbAux.append("xMlj53Hh9CVcuXgDVy5dx60bt/HowUO8evEyATDPnz81YHrFLZdoL14maay5obH+hi15TS2yU23F&#13;");
		sbAux.append("q/jt39XMYwJNHjwVbDx/BtMMgZ9halDxm1xzR8B7U66789duOKdFYwQUwcQwnmEzdRAbK6xdu9Yk&#13;");
		sbAux.append("6bQuh2zEiIoimG6H4KLLY+6FZaZ0gZpdJnBY00xAEUAMpxkFMdnIhCEFMt0VwUJmYjRHYw5Iw266&#13;");
		sbAux.append("L2orgo8ujcKar6WVLfsvw1xNmjQz4T9dFZdi010xtKf7YmWA6SwBx3/q/fsPcEZAcer0WZw4cQpH&#13;");
		sbAux.append("jhwz4npvnOiZXXHYFR0jQDkmYDuKkyeOCUOdwIljJwVoJ+NZ7BgOCZMd2HcYBwWox46ewrkzFw1A&#13;");
		sbAux.append("L124jHPnLpjPSdzl622tc//T26s31Osk4rB4wDyLt794z1StaaaYVhAxK0lddPLkSRPaU1gTSHRx&#13;");
		sbAux.append("ZCfdvZBAUndFEazr08lMvIZAatq0qXFjBA8jMhoBxbpn6iAyD1lJS1i5lJmgoU4iaNgjkQCimM6f&#13;");
		sbAux.append("P785r6tV+dl8X4puZsP5Pfl9GfqfOHHC9P7TCgKtnnRO2fPHwnkhzu/xema1tbqAOSTeZz6Jz3EJ&#13;");
		sbAux.append("05EjR8w1fMz/GyYu2YiUaQa+BycoOcP9thZx/9dvKZz/KL2v4bxzcpH3WbLBXzH/w1i5xgQeczLc&#13;");
		sbAux.append("d4vMRC1ENqL+oeahMdJiWM6SU04jcL6K4TsjNYKGACELseSDzMSVG5yCIGAosummtA0wmYYaiufo&#13;");
		sbAux.append("yggmfiZdJXNGn3zyiWEwulIyJpmT35c/Aue/w3lS2PnHo8/x7+R6N5binjt3zvy9Ok1CQDJ9wEQl&#13;");
		sbAux.append("AUVzBhCNVQMEbHKaVP6fBxD/03QSLak/UGuinf+j+QumKOQUx/nz503eiINFYa1bDOgUBvM5ZA7q&#13;");
		sbAux.append("F2oXztiTrTgFwmuYzWZuh1t7EzQKGG04TtDQhXFujUdqH54no7GRFIU09RNzT7yGqzHIFmQJAsCZ&#13;");
		sbAux.append("WZ3/DmUeXT2iCyeTWkBA1iILs5SEQCJ4+Dczj0Q3yR8TH/MzyUBMxOqKlL8r8yQJoKT+MGe3ptP9&#13;");
		sbAux.append("if+T+QsnkPgfyP9I/krXr19vwMQBJQPRvXDwGX4TFBx4rvnS6RGtSiQzESDUOAQEozEyEiMv5pDI&#13;");
		sbAux.append("VqwCYAKT68UovJmspP7hLDsHmrksflf9e/h9E9dzO9d08+9XS+xynIHF8/rjoYsiOxFU6tbIPvxR&#13;");
		sbAux.append("OTek+DuDJyET7cw+/+ktMZj4XvwFUiMwW0wXx3CfmoTTEQSKzkdR7FI8Eyy6P4bqGbo/aiMykyYf&#13;");
		sbAux.append("OQHKOSueZ/TG8J4ukJEf3SgZgWBW90Q34vwDUdesE8hvWkCpLjypHwwfE3xa/kmXR7aj2yNQNR2S&#13;");
		sbAux.append("mM3/buD5Nw3kzDqqif6oOk31ggpT51+y83+4drlnNEdBqhOf1CnMYFMHEUyMwDgJy6iLjcR1p0JG&#13;");
		sbAux.append("cJz6YNTHXBR1D7PaBBBBx/ch41HrKICdwayLHp2rC5ICRXL3DHHOlyW10CAp0PxdwfOH9UCJwaNa&#13;");
		sbAux.append("SPWCRjDO/7mJmy8knuHnfUY5ZAkyE0Uu80zLli0zIGDkRrdGt8QojPkgTmUwMiPjcGqDiUW6LLpC&#13;");
		sbAux.append("shHdJCMdvq8CRjcK0cd/NICaskjqb3cOKPSH8UcMnfj/IvGKlf9KAP3/uDlXvekvmezEeh41Aopu&#13;");
		sbAux.append("jgKZLosAYpKRUx9cG8byVlY5UjxTuDL6+ef2XwwgFboUpRSjjGiYAadm4v5kBAqZiQKa5a10YWQu&#13;");
		sbAux.append("go3C9Z/bfyGAkhKoKkgpRil8GQozCUgBThfH9ACTlxTiBJpzwds/t785gJw1gALIORr6oxvBxYiK&#13;");
		sbAux.append("moYimUk8vWmo/s/tvwhAzgk1Z3PO/tKcw2DenOuzEy9m1B1m/rn9jQGUGDDJDW0JFN1uyPmcZnh5&#13;");
		sbAux.append("c77/z+1vCqCkZqGTStzpEurEGWMNtzWc1oneP8rH/HP7GwIo8Tzbm9hKgfSm9dl/57zK/7Xb/wLT&#13;");
		sbAux.append("hsEPUSXCzAAAAABJRU5ErkJggg==</y:Resource> ");
		sbAux.append("    </y:Resources> ");
		sbAux.append("  </data> ");
		sbAux.append("</graphml>");
		
		this.footer = sbAux.toString();
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public StringBuilder getContent() {
		return content;
	}

	public void setContent(StringBuilder content) {
		this.content = content;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}
	
	
	public int createRetangulo(String nodeLabel, String content, double altura, double largura, int level){
		nodeCounter++;
		StringBuilder sbAux = new StringBuilder();
		
		double y = height * level; 

		sbAux.append(" <node id=\"n"+nodeCounter+"\"> <data key=\"d6\"><y:GenericNode configuration=\"com.yworks.flowchart.process\">");
		sbAux.append(" <y:Geometry height=\""+altura+"\" width=\""+largura+"\" x=\"1553.504535361963\" y=\""+y+"\"/>");
		sbAux.append(" <y:Fill color=\"#008000\" color2=\"#FFFFFF\" transparent=\"false\"/>");
		sbAux.append(" <y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>");
		
		sbAux.append(" <y:NodeLabel alignment=\"left\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"121.609375\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"956.88671875\" x=\"2.9449624058941026\" y=\"1.280541377731879\">");
		sbAux.append(nodeLabel+"\n");
		sbAux.append(content);
		sbAux.append("</y:NodeLabel> </y:GenericNode> </data> </node>");
		
		this.content.append(sbAux.toString());
		
		return nodeCounter;
	}
	
	private String getElipse(String text,int level){
		StringBuilder sbAux = new StringBuilder();
		
		double y = height * level; 
		
		sbAux.append("<node id=\"n"+nodeCounter+"\">");
		sbAux.append("<data key=\"d6\">");
		sbAux.append("<y:GenericNode configuration=\"com.yworks.flowchart.start1\">");
		sbAux.append("<y:Geometry height=\"51.97047233742546\" width=\"118.17066637064426\" x=\"2019.892857142857\" y=\""+y+"\"/>");
		sbAux.append("<y:Fill color=\"#99CCFF\" color2=\"#99CCFF\" transparent=\"false\"/>");
		sbAux.append("<y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>");
		sbAux.append("<y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"32.681640625\" x=\"-1.3408203125\" y=\"5.6494140625\">"+text+"</y:NodeLabel>");
		sbAux.append("</y:GenericNode>");
		sbAux.append("</data>");
		sbAux.append("</node>");
		
		return sbAux.toString();
	}
	
	public int createElipse(String text,int level){
		nodeCounter++;
		
		this.content.append(getElipse(text,level).replaceAll("#99CCFF", "#FFFFFF"));
	    
	    return nodeCounter;
	}
	
	public int createInicio(String text){
		nodeCounter++;
		
		this.content.append(getElipse(text,1));
	    
	    return nodeCounter;
	}
	
	
	
	public int createLosango(String label, int level){
		nodeCounter++;
		
		label = tratarCaracteresEspeciais(label);
		double y = height * level; 
		StringBuilder sbAux = new StringBuilder();

		sbAux.append("<node id=\"n"+nodeCounter+"\">");
	      sbAux.append("<data key=\"d6\">");
	        sbAux.append("<y:GenericNode configuration=\"com.yworks.flowchart.decision\">");
	          sbAux.append("<y:Geometry height=\"97.10856467692315\" width=\"115.6295885225885\" x=\"1537.109412087912\" y=\""+y+"\"/>");
	          sbAux.append("<y:Fill hasColor=\"false\" transparent=\"false\"/>");
	          sbAux.append("<y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>");
	          sbAux.append("<y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"33.40234375\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"83.728515625\" x=\"15.95053644879431\" y=\"31.853110463461576\">"+label+"</y:NodeLabel>");
	        sbAux.append("</y:GenericNode>");
	      sbAux.append("</data>");
	    sbAux.append("</node>");
		
		this.content.append(sbAux.toString());
	    
	    return nodeCounter;
	}
	
	
	private String tratarCaracteresEspeciais(String label){
		
		String ret = label.replaceAll("<", "menor que");
		ret  = ret.replaceAll(">", "maior que");
		
		return ret;
		
	}
	public int createFlow(String label, int level){
		nodeCounter++;
		StringBuilder sbAux = new StringBuilder();
		double y = height * level; 
		
		sbAux.append("<node id=\"n"+nodeCounter+"\">");
     //   sbAux.append("<data key="d4"><![CDATA[..\mensagens\mensagem_contingencia.graphml]]></data>");
        sbAux.append("<data key=\"d6\">");
          sbAux.append("<y:GenericNode configuration=\"com.yworks.flowchart.offPageReference\">");
            sbAux.append("<y:Geometry height=\"80.0\" width=\"80.0\" x=\"17578.436904761904\" y=\""+y+"\"/>");
            sbAux.append("<y:Fill color=\"#99CCFF\" transparent=\"false\"/>");
            sbAux.append("<y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>");
            sbAux.append("<y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"48.103515625\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"74.048828125\" >");
            sbAux.append(label);
            sbAux.append("</y:NodeLabel>");
          sbAux.append("</y:GenericNode>");
        sbAux.append("</data>");
        sbAux.append("</node>");
		
		this.content.append(sbAux.toString());
	    
	    return nodeCounter;
	}
	
	public int createATH(String label, String ponto, int level){
		nodeCounter++;
		StringBuilder sbAux = new StringBuilder();
		double y = height * level; 
		
		sbAux.append("<node id=\"n"+nodeCounter+"\">");
	     sbAux.append("<data key=\"d4\"><![CDATA[http://sv2kprec1/recall/transf_regras2.aspx?ponto="+ponto+"]]></data>");
	     sbAux.append("<data key=\"d5\"><![CDATA[3]]></data>");
	     sbAux.append("<data key=\"d6\">");
	       sbAux.append("<y:ImageNode>");
	         sbAux.append("<y:Geometry height=\"58.0\" width=\"58.0\" y=\""+y+"\" />");
	         sbAux.append("<y:Fill color=\"#FFCC00\" transparent=\"false\"/>");
	         sbAux.append("<y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>");
	         sbAux.append("<y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"13\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"19.92626953125\" modelName=\"sandwich\" modelPosition=\"s\" textColor=\"#000000\" visible=\"true\" width=\"66.14990234375\" >"+label+"</y:NodeLabel>");
	         sbAux.append("<y:Image refid=\"1\"/>");
	       sbAux.append("</y:ImageNode>");
	     sbAux.append("</data>");
	   sbAux.append("</node>");
		
		this.content.append(sbAux.toString());
	    
	    return nodeCounter;
	}
	
	public int createChoice(String dtmf, String fontSize,int level){
		nodeCounter++;
		StringBuilder sbAux = new StringBuilder();

		double y = height * level; 
		
		 sbAux.append("<node id=\"n"+nodeCounter+"\">");
	      sbAux.append("<data key=\"d6\">");
	        sbAux.append("<y:GenericNode configuration=\"com.yworks.flowchart.onPageReference\">");
	          sbAux.append("<y:Geometry height=\"40.0\" width=\"40.0\" x=\"343.41785714285714\" y=\""+Double.toString(y)+"\"/>");
	          sbAux.append("<y:Fill color=\"#FFFFFF\" color2=\"#FFFFFF\" transparent=\"false\"/>");
	          sbAux.append("<y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>");
	          sbAux.append("<y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\""+fontSize+"\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"28.501953125\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"15.123046875\" x=\"12.4384765625\" y=\"5.7490234375\">"+dtmf+"</y:NodeLabel>");
	        sbAux.append("</y:GenericNode>");
	      sbAux.append("</data>");
	    sbAux.append("</node>\n");
		
	    this.content.append(sbAux.toString());
	    
	    return nodeCounter;
	}
	
	public int createChoice(String dtmf, int level){
		return createChoice(dtmf, "20",level);
	}
	
	public int createOperation(String name){
		nodeCounter++;
		StringBuilder sbAux = new StringBuilder();

		
		 sbAux.append("<node id=\"n"+nodeCounter+"\">");
	      sbAux.append("<data key=\"d6\">");
	        sbAux.append("<y:GenericNode configuration=\"com.yworks.flowchart.manualOperation\">");
	          sbAux.append("<y:Geometry height=\"40.0\" width=\"113.63\" x=\"4742.44\" y=\"-12.94\"/>");
	          sbAux.append("<y:Fill color=\"#FFFFFF\" color2=\"#FFFFFF\" transparent=\"false\"/>");
	          sbAux.append("<y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>");
	          
	          sbAux.append("<y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"custom\" textColor=\"#000000\" visible=\"true\" width=\"26.013671875\" x=\"43.81181548416953\" y=\"10.6494140625\">"+name+"<y:LabelModel>");
			  sbAux.append("<y:SmartNodeLabelModel distance=\"4.0\"/>");
			  sbAux.append("</y:LabelModel>");
			  sbAux.append("<y:ModelParameter>");
			  sbAux.append(" <y:SmartNodeLabelModelParameter labelRatioX=\"0.0\" labelRatioY=\"0.0\" nodeRatioX=\"0.0\" nodeRatioY=\"0.0\" offsetX=\"0.0\" offsetY=\"0.0\" upX=\"0.0\" upY=\"-1.0\"/>");
			  sbAux.append(" </y:ModelParameter>");
			  sbAux.append("</y:NodeLabel>");
	       
	          
	          sbAux.append("</y:GenericNode>");
	      sbAux.append("</data>");
	    sbAux.append("</node>\n");
		
	    this.content.append(sbAux.toString());
	    
	    return nodeCounter;
	}
	
	public int createHungUp(String name, int level){
		nodeCounter++;
		StringBuilder sbAux = new StringBuilder();
		double y = height * level; 
		
		sbAux.append("<node id=\"n"+nodeCounter+"\">");
		sbAux.append("  <data key=\"d6\">");
		sbAux.append("	<y:GenericNode configuration=\"com.yworks.flowchart.terminator\">");
		sbAux.append("	  <y:Geometry height=\"40.0\" width=\"80.0\" x=\"19310.115476190476\" y=\""+y+"\"/>");
		sbAux.append("	  <y:Fill color=\"#FFFF00\" color2=\"#FFFF00\" transparent=\"false\"/>");
		sbAux.append("	  <y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>");
		sbAux.append("	  <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"internal\" modelPosition=\"c\" textColor=\"#000000\" visible=\"true\" width=\"44.01953125\" x=\"17.990234375\" y=\"10.649414062500227\">Desliga</y:NodeLabel> ");
		sbAux.append("	  <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" hasText=\"false\" height=\"4.0\" modelName=\"custom\" textColor=\"#000000\" visible=\"true\" width=\"4.0\" x=\"38.0\" y=\"18.0\">");
		sbAux.append("		<y:LabelModel> ");
		sbAux.append("		  <y:SmartNodeLabelModel distance=\"4.0\"/>");
		sbAux.append("		</y:LabelModel> ");
		sbAux.append("		<y:ModelParameter> ");
		sbAux.append("		  <y:SmartNodeLabelModelParameter labelRatioX=\"0.0\" labelRatioY=\"0.0\" nodeRatioX=\"0.0\" nodeRatioY=\"0.0\" offsetX=\"0.0\" offsetY=\"0.0\" upX=\"0.0\" upY=\"-1.0\"/>");
		sbAux.append("		</y:ModelParameter> ");
		sbAux.append("	  </y:NodeLabel> ");
		sbAux.append("	  <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"custom\" textColor=\"#000000\" visible=\"true\" width=\"61.380859375\" x=\"-60.057921318621084\" y=\"10.649414062500227\"><y:LabelModel> ");
		sbAux.append("		  <y:SmartNodeLabelModel distance=\"4.0\"/>");
		sbAux.append("		</y:LabelModel> ");
		sbAux.append("		<y:ModelParameter> ");
		sbAux.append("		  <y:SmartNodeLabelModelParameter labelRatioX=\"0.47844705874353466\" labelRatioY=\"0.0\" nodeRatioX=\"-0.5\" nodeRatioY=\"0.0\" offsetX=\"0.0\" offsetY=\"0.0\" upX=\"0.0\" upY=\"-1.0\"/>");
		sbAux.append("		</y:ModelParameter> ");
		sbAux.append("	  </y:NodeLabel> ");
		sbAux.append("	</y:GenericNode> ");
		sbAux.append("  </data> ");
		sbAux.append("</node> \n");
		
	    this.content.append(sbAux.toString());
	    
	    return nodeCounter;
	}
	
	public void createFlecha(String label, int l, int m){
		edgeCounter++;
		
		StringBuilder sbAux = new StringBuilder();
		
		if(mapFlecha.contains("n"+l+"n"+m))
				return;
		
		mapFlecha.add("n"+l+"n"+m);
			
		if(isDecision > 0){
			l = isDecision;
		}
		isDecision = 0;
		
		 sbAux.append("<edge id=\"e"+edgeCounter+"\" source=\"n"+l+"\" target=\"n"+m+"\">");
	      sbAux.append("<data key=\"d10\">");
	        sbAux.append("<y:PolyLineEdge>");
	          sbAux.append("<y:LineStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>");
	          sbAux.append("<y:Arrows source=\"none\" target=\"standard\"/>");
	          
	          if(label != null){
			  sbAux.append("<y:EdgeLabel alignment=\"center\" configuration=\"AutoFlippingLabel\" distance=\"2.0\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"48.103515625\" modelName=\"custom\" preferredPlacement=\"anywhere\" ratio=\"0.5\" textColor=\"#000000\" visible=\"true\" width=\"167.44140625\" x=\"-188.79196474979562\" y=\"-34.49245494405295\"> ");
			  
				  sbAux.append("Tag "+label);
			 
				  sbAux.append("<y:LabelModel>");
				  sbAux.append(" <y:SmartEdgeLabelModel autoRotationEnabled=\"false\" defaultAngle=\"0.0\" defaultDistance=\"10.0\"/>");
				  sbAux.append(" </y:LabelModel>");
				  sbAux.append(" <y:ModelParameter>");
				  sbAux.append("  <y:SmartEdgeLabelModelParameter angle=\"6.283185307179586\" distance=\"10.440773110464265\" distanceToCenter=\"true\" position=\"right\" ratio=\"0.736180072127879\" segment=\"0\"/>");
				  sbAux.append(" </y:ModelParameter>");
				  sbAux.append(" <y:PreferredPlacementDescriptor angle=\"0.0\" angleOffsetOnRightSide=\"0\" angleReference=\"absolute\" angleRotationOnRightSide=\"co\" distance=\"-1.0\" frozen=\"true\" placement=\"anywhere\" side=\"anywhere\" sideReference=\"relative_to_edge_flow\"/>");
				  sbAux.append(" </y:EdgeLabel>");
	          }
	          sbAux.append("<y:BendStyle smoothed=\"false\"/>");
	        sbAux.append("</y:PolyLineEdge>");
	      sbAux.append("</data>");
	    sbAux.append("</edge>\n");
		
		
		  this.content.append(sbAux.toString());
	}
	
	public void createXMLFile(){
		File file = null;
		
		file = new File("/Temp/teste.graphml");
		BufferedWriter out = null;
		try {
			file.createNewFile();
			out = new BufferedWriter(new FileWriter(file));
			out.write(this.header+this.content+this.footer);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public int getNodeCounter() {
		return nodeCounter;
	}

	public void setNodeCounter(int nodeCounter) {
		this.nodeCounter = nodeCounter;
	}

	public int getEdgeCounter() {
		return edgeCounter;
	}

	public void setEdgeCounter(int edgeCounter) {
		this.edgeCounter = edgeCounter;
	}
	
	@Override
	public String output(List<FluxElement> listElements) {

		map = new HashMap<String, String>();
		mapFlecha = new ArrayList<String>();
		for(FluxElement fluxElement : listElements)
		{
			try{

				if(fluxElement.getId() == 0)
					continue;
				else if(map.get(Long.toString(fluxElement.getId())) != null){
						createFlecha(fluxElement.getParent().getTag(), getLastId(fluxElement), Integer.parseInt(map.get(Long.toString(fluxElement.getId()))));
				}
			
				fluxElement.setName(new String(fluxElement.getName().getBytes("UTF-8"),"UTF-8"));
				fluxElement.setDescription(new String(fluxElement.getDescription().getBytes("UTF-8"),"UTF-8").replace("<br />", "\n"));
				
					if(
							fluxElement.getType().equalsIgnoreCase(Menu.class.getSimpleName()) || 
							fluxElement.getType().equalsIgnoreCase(Announce.class.getSimpleName()) || 
							fluxElement.getType().equalsIgnoreCase(PromptCollect.class.getSimpleName()) 
					)
					{
						
						if(checkDuplicate(fluxElement)){
							String desc = breakFrases(fluxElement.getDescription());
							Retangulo ret = dimensionaRetangulo(Arrays.asList(desc.split("\n")), fluxElement.getName());
							
							auxId = createRetangulo("["+fluxElement.getName()+"]", desc, ret.getAltura(), ret.getLargura(),getLevel(fluxElement));
						}
							
						if(fluxElement.getParent() != null ){
							createFlecha(fluxElement.getParent().getTag(), getLastId(fluxElement), auxId);
							
						}
						
					} else if (
							fluxElement.getType().equalsIgnoreCase(Choice.class.getSimpleName()) ||
							fluxElement.getType().equalsIgnoreCase(NoMatchInput.class.getSimpleName())
					){
						
						if(checkDuplicate(fluxElement))
							auxId = createChoice(fluxElement.getName(),getLevel(fluxElement));
						
						if(fluxElement.getParent() != null ){
							createFlecha(fluxElement.getParent().getTag(), getLastId(fluxElement), auxId);
							
						}
						
					}  else if (fluxElement.getType().equalsIgnoreCase(Flow.class.getSimpleName()) || fluxElement.getType().equalsIgnoreCase(Operation.class.getSimpleName())){
						
						if(checkDuplicate(fluxElement))
							auxId = createFlow(fluxElement.getDescription(),getLevel(fluxElement));
						
						if(fluxElement.getParent() != null ){
							createFlecha(fluxElement.getParent().getTag(), getLastId(fluxElement), auxId);
							
						}
					} else if (fluxElement.getType().equalsIgnoreCase(DecisionGroup.class.getSimpleName()) || fluxElement.getType().equalsIgnoreCase(Decision.class.getSimpleName())){
						
						if(checkDuplicate(fluxElement))
							auxId = createLosango(fluxElement.getDescription(),getLevel(fluxElement));
						
						if( fluxElement.getParent() != null ){
							createFlecha(fluxElement.getParent().getTag(), getLastId(fluxElement), auxId);
							
						}
						
					}  else if (fluxElement.getType().equalsIgnoreCase(Transfer.class.getSimpleName())){
						
						if(checkDuplicate(fluxElement))
							auxId = createATH(fluxElement.getName(),fluxElement.getAux(),getLevel(fluxElement));
						
						if(fluxElement.getParent() != null){
							createFlecha(fluxElement.getParent().getTag(), getLastId(fluxElement), auxId);
							
						}
						
					} else if (fluxElement.getType().equalsIgnoreCase(Disconnect.class.getSimpleName())){
						
						if(checkDuplicate(fluxElement))
							auxId = createHungUp(fluxElement.getName(),getLevel(fluxElement));
						
						if(fluxElement.getParent() != null  ){
							createFlecha(fluxElement.getParent().getTag(), getLastId(fluxElement), auxId);
							
						}
						
					} else {
						System.out.println(fluxElement.getType());
						break;
					} 
			
			} catch(Exception e)
			{
				fluxElement.getType();
				e.getStackTrace();
			}
			
			map.put(Integer.toString(auxId), Long.toString(fluxElement.getId()));
			map.put(Long.toString(fluxElement.getId()),Integer.toString(auxId));
			isFirst = false;
		}
	
		//createXMLFile();
		return this.header+this.content+this.footer;
	}
	
	private boolean checkDuplicate(FluxElement fluxElement)
	{
		boolean result = false;
		if(map.get(Long.toString(fluxElement.getId())) == null)
			return true;
		else{
			auxId = Integer.parseInt(map.get(Long.toString(fluxElement.getId())));
		}
		
		return result;
	}
	
	private int getLevel(FluxElement element)
	{
		int result = 1;
		FluxElement aux = element.getParent();
		if(aux != null)
			
			while(aux != null)
			{
				aux = aux.getParent();
				result++;
			}
		return result;
	}
	
	private int getLastId(FluxElement element)
	{
		int result = 0;
		String id  = "";
		if(element.getParent() != null)
			id = Long.toString(element.getParent().getId());
		
		if(!id.isEmpty())
			result =Integer.parseInt(map.get(id));
		
		return result;
	}

	private Retangulo dimensionaRetangulo(List<String> frases, String header){
		Retangulo ret = new Retangulo();
		double altura = Constantes.MARGEM_ALTURA_PIXELS + frases.size() * Constantes.FATOR_ALTURA_PIXELS;
		double largura = Constantes.MARGEM_LARGURA_PIXELS;
		
		int auxLength = 0;
		for(String frase : frases){
			if(frase.length() > auxLength){
				auxLength = frase.length();
			}
		}
		
		/*if necessario pq a frase pode vir com as linhas ja quebradas
		assim criando um retangulo muito mais largo do que o necessario*/
		if(auxLength > Constantes.MAX_FRASE_LENGTH){
			//largura +=  Constantes.MAX_FRASE_LENGTH * Constantes.FATOR_LARGURA_PIXELS;
			largura +=  auxLength * 9;
		}else{
			largura += auxLength * Constantes.FATOR_LARGURA_PIXELS;
		}
		
		double larguraHeader = header.length() * Constantes.FATOR_LARGURA_PIXELS;
		if(largura < larguraHeader){
			largura += larguraHeader;
		}
			
		ret.setLargura(largura);
		ret.setAltura(altura);
		
		return ret;
	}
	
	private String breakFrases(String frase){
		frase = StringUtils.stripAccents(frase.replace("<br />","\n"));
		List<String> subFrases = new ArrayList<String>();
		if(frase != null){
			frase = frase.replaceAll("\r", "");
		}
		
		return frase;
	}
	
}
