package tacos.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tacos.IngredientRef;
import tacos.Taco;
import tacos.TacoOrder;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final JdbcOperations jdbcOperations;

    @Autowired
    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {

        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "Insert into Taco_Order (delivery_name, delivery_street, delivery_city, " +
                        "delivery_state, delivery_zip, cc_number, cc_expiration, cc_cvv , placed_at) " +
                        "values(?,?,?,?,?,?,?,?,?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);

        pscf.setReturnGeneratedKeys(true);
        order.setPlacedAt(new Date());

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                        order.getDeliveryName(),
                        order.getDeliveryStreet(),
                        order.getDeliveryCity(),
                        order.getDeliveryState(),
                        order.getDeliveryZip(),
                        order.getCcNumber(),
                        order.getCcExpiration(),
                        order.getCcCVV(),
                        order.getPlacedAt())
        );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);

        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        int i = 0;
        for (Taco taco : order.getTacos()) {
            saveTaco(orderId, i++, taco);
        }

        return order;
    }

    private long saveTaco(long orderId, int orderKey, Taco taco) {

        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "Insert into Taco (name, created_at, taco_order, taco_order_key) " +
                        "values(?,?,?,?)",
                Types.VARCHAR,Types.TIMESTAMP ,Types.BIGINT,Types.BIGINT
        );

        taco.setCreatedAt(new Date());
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(taco.getName(),
                        taco.getCreatedAt(),
                        orderId,
                        orderKey)
        );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);

        long tacoId = keyHolder.getKey().intValue();
        taco.setId(tacoId);

        saveIngredientRefs(tacoId, taco.getIngredients());

        return tacoId;
    }

    private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredients) {

        int key = 0;
        for (IngredientRef ingredient : ingredients) {

            jdbcOperations.update("insert into Ingredient_Ref " +
                            "(ingredient, taco, taco_key) values (?,?,?)",
                    ingredient.getIngredient(), tacoId, key++);
        }
    }
}
