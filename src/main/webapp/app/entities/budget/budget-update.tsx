import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBankCategory } from 'app/shared/model/bank-category.model';
import { getEntities as getBankCategories } from 'app/entities/bank-category/bank-category.reducer';
import { getEntity, updateEntity, createEntity, reset } from './budget.reducer';
import { IBudget } from 'app/shared/model/budget.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BudgetUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bankCategories = useAppSelector(state => state.bankCategory.entities);
  const budgetEntity = useAppSelector(state => state.budget.entity);
  const loading = useAppSelector(state => state.budget.loading);
  const updating = useAppSelector(state => state.budget.updating);
  const updateSuccess = useAppSelector(state => state.budget.updateSuccess);
  const handleClose = () => {
    props.history.push('/budget');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBankCategories({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.budgetDate = convertDateTimeToServer(values.budgetDate);

    const entity = {
      ...budgetEntity,
      ...values,
      category: bankCategories.find(it => it.id.toString() === values.category.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          budgetDate: displayDefaultDateTime(),
        }
      : {
          ...budgetEntity,
          budgetDate: convertDateTimeFromServer(budgetEntity.budgetDate),
          category: budgetEntity?.category?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.budget.home.createOrEditLabel" data-cy="BudgetCreateUpdateHeading">
            <Translate contentKey="akountsApp.budget.home.createOrEditLabel">Create or edit a Budget</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="budget-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.budget.budgetDate')}
                id="budget-budgetDate"
                name="budgetDate"
                data-cy="budgetDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.budget.categorieLabel')}
                id="budget-categorieLabel"
                name="categorieLabel"
                data-cy="categorieLabel"
                type="text"
              />
              <ValidatedField label={translate('akountsApp.budget.amount')} id="budget-amount" name="amount" data-cy="amount" type="text" />
              <ValidatedField
                id="budget-category"
                name="category"
                data-cy="category"
                label={translate('akountsApp.budget.category')}
                type="select"
              >
                <option value="" key="0" />
                {bankCategories
                  ? bankCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/budget" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BudgetUpdate;
